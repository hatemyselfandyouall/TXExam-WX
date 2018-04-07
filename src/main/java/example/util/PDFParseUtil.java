package example.util;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * 博圣的PDF报告解析器-以及一些文件处理的方法
 *
 * 好吧现在越来越多,不论如何本类只管PDF及文件操作
 *
 * @author gzd
 */
public class PDFParseUtil {

    private static PDFParseUtil pdfParseUtil = new PDFParseUtil();

    private PDFParseUtil() {
    }

    public static PDFParseUtil getPDFParser() {
        return pdfParseUtil;
    }

    public static void main(String[] args) throws Exception {
        getPDFParser().FilesNameChange("E:\\exam-ltj");
    }

//    /**
//     * 遍历指定目录下所有文件,生成样本列表并返回
//     *
//     * @param path
//     * @return
//     */
//    public Map<String, Object> parseFiles(String path) {
//        int count = 0, success = 0;
//        List<ReportInfo> reportInfoModels = new ArrayList<>();
//        File file = new File(path);
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            for (File pdf : files) {
//                if (pdf.isDirectory()) {
//                    continue;
//                } else {
//                    count++;
//                    System.out.println("开始解析文件:" + pdf.getAbsolutePath());
//                    try {
//                        ReportInfo reportInfo = parsePDF(pdf);
//                        if (reportInfo != null) {
//                            reportInfo.setPDFPath(pdf.getPath());
//                            reportInfoModels.add(reportInfo);
//                        }
//                        success++;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        continue;
//                    }
//                }
//            }
//        } else {
//        }
//        Map<String, Object> reportInfoMap = new HashMap<>();
//        reportInfoMap.put("count", count);
//        reportInfoMap.put("parseSuccess", success);
//        reportInfoMap.put("resultList", reportInfoModels);
//        return reportInfoMap;
//    }

    /**
     * 将压缩文件流解压到指定的PDF路径下
     *
     * @param fileName
     * @param source
     * @param targetPath
     * @return
     * @throws Exception
     */
    public boolean decompressionFiles(String fileName, InputStream source, String targetPath) throws Exception {
        delAllFile(targetPath);//清空文件夹
        String[] name = fileName.split("\\.");
        if (name[name.length - 1].equals("rar")) {
            return unRar(source, targetPath);
        }
        if (name[name.length - 1].equals("zip")) {
            return unzip(source, targetPath);
        }
        return false;
    }

    /**
     * 删除目录下所有文件
     */
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * zip格式的压缩文件解压
     *
     * @throws Exception
     */
    public boolean unzip(InputStream in, String unzipFilePath) throws Exception {
        Charset charset = Charset.forName("gbk");
        ZipInputStream zipIn = new ZipInputStream(in, charset);
        ZipEntry zipEntry;
        FileOutputStream out = null;
        try {
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    return false;
                } else {
                    String iconUrl = unzipFilePath + File.separator + zipEntry.getName().split("\\.")[0] + getTimeStamp() + "." + zipEntry.getName().split("\\.")[1];
                    File file = new File(iconUrl);
                    file.createNewFile();
                    out = new FileOutputStream(file);
                    int b = 0;
                    while ((b = zipIn.read()) != -1) {
                        out.write(b);
                    }
                    out.close();
                }
            }
        } catch (Exception ex) {
            return false;
        } finally {
            IOUtils.closeQuietly(zipIn);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return true;
    }

    /**
     * rar格式的压缩文件解压
     *
     * @throws Exception
     */
    public boolean unRar(InputStream in, String destDir)
            throws Exception {
        Archive a = null;
        FileOutputStream fos = null;
        File file = getFile(destDir, in);
        try {
            a = new Archive(file);
            FileHeader fh = a.nextFileHeader();

            while (fh != null) {
                if (!fh.isDirectory()) {
                    // 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
                    String compressFileName = fh.getFileNameW().trim();
                    if (compressFileName.equals("")) {
                        compressFileName = fh.getFileNameString().trim();
                    }
                    String destFileName = "";
                    String destDirName = "";
                    // 非windows系统
                    if (File.separator.equals("/")) {
                        destFileName = destDir + "//" + compressFileName.replaceAll("\\\\", "/");
                        destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
                        // windows系统
                    } else {
                        destFileName = destDir + "/" + compressFileName.replaceAll("/", "\\\\");
                        destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\\\"));
                    }
                    // 2创建文件夹
                    File dir = new File(destDirName);
                    if (!dir.exists() || !dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    // 3解压缩文件
                    String[] temp = destFileName.split("\\.");
                    destFileName = destFileName.replace(destFileName.split("\\.")[temp.length - 1], "") + getTimeStamp() + "." + destFileName.split("\\.")[temp.length - 1];
                    File targetFile = new File(destFileName);
                    if (!targetFile.exists()) {
                        targetFile.createNewFile();
                    }
                    fos = new FileOutputStream(targetFile);
                    a.extractFile(fh, fos);
                    fos.close();
                    fos = null;
                }
                fh = a.nextFileHeader();
            }
            a.close();
            a = null;
            return true;
        } catch (Exception e) {
            throw new Exception("Rar格式解压失败！");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (a != null) {
                    a.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 输入流转化为FIle类
     *
     * @param in
     * @return
     */
    public File getFile(String targetPath, InputStream in) throws IOException {
        int bufferSize = 1024;
        File zipFile = new File(targetPath + "//temp//temp.rar");
        File temp = new File(targetPath + "//temp");
        if (!temp.exists()) {
            temp.mkdirs();
        }
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        } else {
            zipFile.delete();
            zipFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(zipFile);
        int bytesRead = 0;
        byte[] buffer = new byte[bufferSize];
        while ((bytesRead = in.read(buffer, 0, bufferSize)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        in.close();
        return zipFile;
    }

    /**
     * 输入流转化为FIle类
     *
     * @param in
     * @return
     */
    public File getFile2(String path, String fileName, InputStream in) throws IOException {
        int bufferSize = 1024;
        File directory = new File(path);
        File pdfFile = new File(path + File.separator + fileName + getTimeStamp() + ".pdf");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (!pdfFile.exists()) {
            pdfFile.createNewFile();
        } else {
            pdfFile.delete();
            pdfFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(pdfFile);
        int bytesRead = 0;
        byte[] buffer = new byte[bufferSize];
        while ((bytesRead = in.read(buffer, 0, bufferSize)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        in.close();
        return pdfFile;
    }


    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public String getTimeStamp() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(now);
        Random random = new Random();
        int randomNumber = random.nextInt(10) * 100 + random.nextInt(10) * 10 + random.nextInt(10);
        return str + randomNumber;
    }

    /**
     * 于指定路径保存PDF文件
     *
     * @param fileByte
     * @param path
     * @param barCode
     * @return
     */
    public String saveFile(byte[] fileByte, String path, String barCode) {
        File FilePath = new File(path);
        if (!FilePath.isDirectory()) {
            FilePath.mkdirs();
        }
        String fileName = path + File.separator + barCode + ".PDF";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                OutputStream out = new FileOutputStream(file);
                out.write(fileByte);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 于指定路径保存制定文件名/类型的文件
     *
     * @param fileByte
     * @param path
     * @return
     */
    public String saveAllFile(byte[] fileByte, String path, String fileName) throws IOException {
        File FilePath = new File(path);
        if (!FilePath.isDirectory()) {
            FilePath.mkdirs();
        }
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream out = new FileOutputStream(file);
        out.write(fileByte);
        out.close();
        return fileName;
    }

    /**
     * 将两个PDF合二为一
     *
     * @param files
     * @param targetPath
     * @return
     * @throws Exception
     */
    public static String PDFMerge(String[] files, String targetPath) throws Exception {

        PDFMergerUtility mergePdf = new PDFMergerUtility();

        String destinationFileName = "mergedTest.pdf";


        for (int i = 0; i < files.length; i++) {
            mergePdf.addSource(files[i]);
        }


        mergePdf.setDestinationFileName(targetPath);
        mergePdf.mergeDocuments();
        for (String temp : files) {
            File file = new File(temp);
            file.delete();
        }
        System.out.print("done");
        return targetPath;
    }

    /**
     * 移动文件路径
     *
     * @param sourcePath
     * @param targetPath
     * @return
     */
    public boolean FilePathChange(String sourcePath, String targetPath) {
        try {
            File afile = new File(sourcePath);
            if (afile.renameTo(new File(targetPath))) {
                System.out.println("File is moved successful!");
                return true;
            } else {
                System.out.println("File is failed to move!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存PDF
     *
     * @param PDFS
     * @return
     */
    public String SavePDFs(String path, List<byte[]> PDFS, String assayBarCode) {
        if (PDFS == null || PDFS.size() == 0) {
            return null;
        } else {
            if (PDFS.size() == 1) {
                return PDFParseUtil.getPDFParser().saveFile(PDFS.get(0), path, assayBarCode + PDFParseUtil.getPDFParser().getTimeStamp());
            } else {
                String[] tempFiles = new String[PDFS.size()];
                String targetPath = path + File.separator + assayBarCode + PDFParseUtil.getPDFParser().getTimeStamp() + ".pdf";
                for (int i = 0; i < PDFS.size(); i++) {
                    tempFiles[i] = PDFParseUtil.getPDFParser().saveFile(PDFS.get(i), path, assayBarCode + i);
                }

                try {
                    return PDFParseUtil.getPDFParser().PDFMerge(tempFiles, targetPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

//    /**
//     * 将PDF文件解析为样本
//     *
//     * @return
//     */
//    public ReportInfo parsePDF(File pdfFile) throws IOException {
//        PDDocument document = null;
//        ReportInfo reportInfoModel = new ReportInfo();
//        ReportItems rptItem = new ReportItems();
//        ReportItemChild rptChildItem = new ReportItemChild();
//        List<ReportItems> reportItemses = new ArrayList<>();
//        List<ReportItemChild> reportChildList = new ArrayList<>();
//        reportInfoModel.setPatientName("");
//        try {
//            document = PDDocument.load(pdfFile);
//
//            // 获取页码
//            int pages = document.getNumberOfPages();
//
//            // 读文本内容
//            PDFTextStripper stripper = new PDFTextStripper();
//            // 设置按顺序输出
//            stripper.setSortByPosition(true);
//            stripper.setStartPage(1);
//            stripper.setEndPage(pages);
//            String content = stripper.getText(document);
//            reportInfoModel.setStatus(4);
//            if (content.contains("样本检测终止报告")) {
//                reportInfoModel.setStatus(2);
//                reportInfoModel.setExplain(getSubUtilSimple(content, "图片解释：(.*?)报告时间"));
//                reportInfoModel.setRejectReason(getSubUtilSimple(content, "终止原因：(.*?)图片解释"));
//                rptChildItem.setRemark(getSubUtilSimple(content, "备注：(.*?)[0-9] /"));
//                reportInfoModel.setAssayBarCode(getSubUtilSimple(content, "条码号： (.*?)\\n"));//正则获取机构条码号
//                rptItem.setAssayItemName(getSubUtilSimple(pdfFile.getName(), "(.*?)检测报告"));//正则获取机构检验项
//            }
//            if (content.contains("样本不合格告知单")) {
//                reportInfoModel.setStatus(3);
//            }
//            if (content.contains("检测报告")) {
//                reportInfoModel.setStatus(1);
//                reportInfoModel.setAssayBarCode(getSubUtilSimple(content, "条码号： (.*?)\\n"));//正则获取机构条码号
//                rptItem.setAssayItemName(getSubUtilSimple(pdfFile.getName(), "(.*?)检测报告"));//正则获取机构检验项目
//                rptChildItem.setResult(getSubUtilSimple(content, "检测结果描述: (.*?)\\n"));
//                rptChildItem.setResultFlag(getSubUtilSimple(content, "检测结果： (.*?)\\n"));
//                rptChildItem.setSuggestion(getSubUtilSimple(content, "临床建议： (.*?)\\n"));
//                rptChildItem.setDescription(getSubUtilSimple(content, "临床症状描述： (.*?)\\n"));
//                rptChildItem.setRemark(getSubUtilSimple(content, "备注：(.*?)[0-9] /"));
//                reportInfoModel.setExplain(getSubUtilSimple(content, "说明：(.*?)报告时间"));
//                String reportDate = getSubUtilSimple(content, "报告时间： (.*?) 检验者：");
//                reportInfoModel.setReportDate(DateUtil.getyyyy_MM_dd(reportDate));
//            }
//            rptItem.setReportId(reportInfoModel.getId());
//            rptChildItem.setReportItemId(rptItem.getId());
//            reportItemses.add(rptItem);
//            reportChildList.add(rptChildItem);
//            reportInfoModel.setItemList(reportItemses);
//            reportInfoModel.setChildList(reportChildList);
//            System.out.println(content);
//            return reportInfoModel;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            document.close();
//        }
//    }

    /**
     * 将PDF转化为图片
     *
     * @param file
     * @return
     */
    public List<String> PDFToImg(File file) {
        try (PDDocument doc = PDDocument.load(file)) {
            List<String> list = new ArrayList<>();
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 96); // Windows native DPI
                BufferedImage srcImage = resize(image, 800, 1200);//产生缩略图
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(srcImage, "PNG", out);
                list.add(b64.encodeAsString(out.toByteArray()));
                out.close();
            }
            doc.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 调整图片样式
     *
     * @param source
     * @param targetW
     * @param targetH
     * @return
     */
    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 如题
     *
     * @param filePath
     * @return
     */
    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 将目录下文件名字改变,特化
     * @param path
     */
    public void FilesNameChange(String path) throws IOException {
        String targetPaths="E:\\jpgs";
        File target=new File(targetPaths);
        if (!target.exists()){
            target.mkdirs();
        }
        File targetPath=new File(path);
        if (!targetPath.exists()||!targetPath.isDirectory()){
            return;
        }else {
            int i=0;
            for (File file:targetPath.listFiles()){
                i++;
                File newName=new File(target+File.separator+(i)+".jpg");
                file.renameTo(newName);
            }
        }

    }

    /**
     * 图片转换为String
     * @param path
     */
    public void Image2Str(String path) throws IOException {
        String type = PDFParseUtil.getSubUtilSimple(path, "/(.*?);");
        File file=new File(path);

    }

   
}





