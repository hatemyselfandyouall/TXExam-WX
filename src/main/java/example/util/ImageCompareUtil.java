package example.util;

import net.sf.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by fox on 2017/10/17.
 */
public class ImageCompareUtil {

    public static void main(String[] args) throws Exception {
//        cutExamImgs("E:\\jpgs","E:\\resultjpg");
//            shortImgs("E:\\resultjpg",75,75);
        String proImage="E:\\test1.png";
////        String testImage="E:\\test2.png";
////        PDFParseUtil.copyFile(proImage,testImage);
//        String targetCutpath="E:\\cutedImg.png";
        ImageCompareUtil.changeImagePixel(proImage, "png");
        ImageCompareUtil.monochrome(proImage, "png");
        ImageCompareUtil.sizeOne(proImage);
        ImageCompareUtil.showColor(proImage);

//        new ImageUtil().cutImage(proImage, targetCutpath,0,30,600,600,"png");
    }

    public static String[][] getPX(String args) {
        int[] rgb = new int[3];

        File file = new File(args);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        String[][] list = new String[width][height];
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                if(!map.containsKey(pixel)){
                    map.put(pixel,1);
                }else {
                    map.put(pixel,map.get(pixel)+1);
                }
                list[i][j] = pixel+"";

            }
        }
        System.out.println(args + map);
        return list;

    }

    /**
     * 图像比较之前进行预处理
     * @param testImage
     * @param proImage
     * @throws Exception
     */
    public static void porCompareImage(String proImage,String testImage) throws Exception {
        String cutPro=proImage.split("\\.")[proImage.split("\\.").length-2]+"_cut"+"."+proImage.split("\\.")[proImage.split("\\.").length-1];//原图剪切后路径
        String cutTest=testImage.split("\\.")[testImage.split("\\.").length-2]+"_cut"+"."+testImage.split("\\.")[testImage.split("\\.").length-1];//测试图剪切后路径
        String proType=new ImageUtil().getFileExt(proImage);//原图真实类型
        String testType=new ImageUtil().getFileExt(proImage);//测试图真实类型
        ImageCompareUtil.changeImagePixel(proImage,proType);//灰化
        ImageCompareUtil.monochrome(proImage, proType);//黑白化
        JSONObject parseResult=ImageCompareUtil.getData(proImage);//获取边缘范围
        new ImageUtil().cutImage(proImage, cutPro, Integer.valueOf(parseResult.get("leftest").toString()), Integer.valueOf(parseResult.get("lowest").toString()),
                Integer.valueOf(parseResult.get("length").toString()), Integer.valueOf(parseResult.get("length").toString()),proType);//剪切
        ImageCompareUtil.changeImagePixel(testImage, testType);//灰化
        ImageCompareUtil.monochrome(testImage, testType);//黑白化
        parseResult=ImageCompareUtil.getData(testImage);//获取边缘范围
        new ImageUtil().cutImage(testImage, cutTest, Integer.valueOf(parseResult.get("leftest").toString()), Integer.valueOf(parseResult.get("lowest").toString()),
                Integer.valueOf(parseResult.get("length").toString()), Integer.valueOf(parseResult.get("length").toString()),testType);//剪切
        ImageCompareUtil.cunImage(proImage, testImage);//保证测试图与原图等大
    }

    /**
     * 图像比较
     * @param imgPath1
     * @param imgPath2
     * @return
     */
    public static String compareImage(String imgPath1, String imgPath2){
        String[] images = {imgPath1, imgPath2};
        if (images.length == 0) {
            System.out.println("Usage >java BMPLoader ImageFile.bmp");
            System.exit(0);
        }

        // 分析图片相似度 begin
        String[][] list1 = getPX(images[0]);
        String[][] list2 = getPX(images[1]);
        int xiangsi = 0;
        int busi = 0;
        for (int i=0;i<list1.length;i++){
            for(int j=0;j<list1[i].length;j++){
                if (list1[i][j].equals(list2[i][j])){
                    xiangsi++;
                }else {
                    busi++;
                }
            }
        }
        String baifen = "";
        try {
            baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");
            baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);
        } catch (Exception e) {
            baifen = "0";
        }
        if (baifen.length() <= 0) {
            baifen = "0";
        }
        if(busi == 0){
            baifen="100";
        }

//        return "相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + Integer.parseInt(baifen) + "%";
        return getScore(Integer.parseInt(baifen));
    }
    public static void cunImage(String proFile,String testFile) throws IOException {
        File file = new File(proFile);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = bi.getWidth();
        int height = bi.getHeight();
        File testImage=new File(testFile);
        try {
            bi = ImageIO.read(testImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(width==bi.getWidth()&&height==bi.getHeight()){
            return;
        }
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                 //绘制改变尺寸后的图
        tag.getGraphics().drawImage(bi, 0, 0,width, height, null);
                 //输出流
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(testFile));
                 //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                //encoder.encode(tag);
        ImageIO.write(tag, "jpg", out);
        out.close();
    }

    public static void  getImagePixel(String image) throws Exception {
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);

//                System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + ","
//                        + rgb[1] + "," + rgb[2] + ")" + pixel);
            }
        }
    }
    public static  void  changeImagePixel(String image,String proType) throws Exception {
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bi=grayImage(bi);
        ImageIO.write(bi, proType, new File(image));

    }
    /**
     * 对彩色照片黑白处理
     *
     * @param srcImg
     * @return
     */
    public static BufferedImage grayImage(final BufferedImage srcImg) {
        int iw = srcImg.getWidth();
        int ih = srcImg.getHeight();
        Graphics2D srcG = srcImg.createGraphics();
        RenderingHints rhs = srcG.getRenderingHints();
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp theOp = new ColorConvertOp(cs, rhs);
        BufferedImage dstImg = new BufferedImage(iw, ih,
                BufferedImage.TYPE_INT_RGB);
        theOp.filter(srcImg, dstImg);
        return dstImg;
    }

    public static BufferedImage monochrome(String imgFile,String proType) throws IOException {
        File file = new File(imgFile);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int[] pixels = new int[width * height];
        bi.getRGB(0, 0, width, height, pixels, 0, width);
        int newPixels[] = new int[width * height];
                for(int i = 0; i < width * height; i++) {
                        newPixels[i] = convertToBlackWhite(pixels[i]);
                    }

                bi = new BufferedImage(width, height,
                                BufferedImage.TYPE_INT_RGB);
                bi.setRGB(0, 0, width, height, newPixels, 0, width);
        ImageIO.write(bi, proType, new File(imgFile));
                return bi;
            }

    private static int convertToBlackWhite(int pixel) {
        int result = 0;
        //int alpha = (pixel >> 24) & 0xff; // not used
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        result = 0xff000000; // 这样，白色就为全F，即 -1

        int tmp = red * red + green * green + blue * blue;
        if(tmp > 3*128*128){ // 大于，则是白色
            result += 0x00ffffff;
        } else { // 是黑色

        }
        return result;
    }


    public static BufferedImage sizeOne(String imgFile) {
        File file = new File(imgFile);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int[] pixels = new int[width * height];
        bi.getRGB(0, 0, width, height, pixels, 0, width);
        int newPixels[] = new int[width * height];
        for(int i = 0; i < width * height; i++) {
            newPixels[i] = convertToBlackWhite(pixels[i]);
        }

        bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, width, height, newPixels, 0, width);
//        ImageIO.write(bi, "jpg",new File(imgFile));
        return bi;
    }

    public static JSONObject getData(String path){
        JSONObject result=new JSONObject();
        Integer highest=0;
        Integer leftest=0;
        Integer lowest=0;
        Integer rightest=0;
        try{
            BufferedImage bimg = ImageIO.read(new File(path));
            int [][] data = new int[bimg.getWidth()][bimg.getHeight()];
            //方式一：通过getRGB()方式获得像素矩阵
            //此方式为沿Height方向扫描
            System.out.println(bimg.getWidth()+"Df"+bimg.getHeight());
//            File testFile=new File("E:\\testImage.txt");
//            if (!testFile.exists()) {
//                testFile.createNewFile();
//            }
//            FileOutputStream fileOutputStream=new FileOutputStream(testFile);
            for(int i=0;i<bimg.getWidth();i++){
                String testimg="";
                for(int j=0; j <bimg.getHeight();j++){
                    if(data[i][j]==0xff000000){
                        testimg+="*";
                    }else {
                        testimg+=".";
                    }
//                    System.out.println(testimg);

//                    fileOutputStream.write(testimg.getBytes());
                    data[i][j]=bimg.getRGB(i,j);
                    //输出一列数据比对
                    if(data[i][j]==0xff000000){
                        if (leftest==0){
                            leftest=i;
                        }
                        if (lowest==0){
                            lowest=j;
                        }
                        if (leftest>i){
                            leftest=i;
                        }
                        if (rightest<i){
                            rightest=i;
                        }
                        if (highest<j){
                            highest=j;
                        }
                        if (lowest>j){
                            lowest=j;
                        }
                    }

                }
            }
            Integer length=0;
            if((highest-lowest)>(rightest-leftest))//此时已取得四个坐标点以及长度,为了切成正方形,应进行偏移
            {
                length=highest-lowest;
                leftest=leftest-(((highest-lowest)-(rightest-leftest))/2);
                if (leftest<0){
                    leftest=0;
                }
            }else {
                length=rightest-leftest;
                lowest-=((rightest-leftest)-(highest-lowest))/2;
                if (lowest<0){
                    lowest=0;
                }
            }
            result.put("lowest",lowest);//边缘扩大
            result.put("leftest",leftest);
            result.put("length",length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getScore(Integer score){
        if(score<80){
            return "分数为"+score+",抱歉,您的临摹没有达到标准";
        }
        if (score<90){
            return "分数为"+score+",恭喜,您的临摹已经很接近真迹了";
        }
        return "分数为"+score+",恭喜,您的临摹简直和真迹一模一样";
    }

    /**
     * 兰亭集序特化
     */
    public static void cutExamImgs(String path,String targetPath1) throws Exception {
        File startPath=new File(path);
        File targetPath=new File(targetPath1);
        if (!targetPath.exists()||!targetPath.isDirectory()){
            targetPath.mkdirs();
        }else {
            for (File file:startPath.listFiles()){
                String test1=path+File.separator+file.getName();
                String test2=targetPath1+File.separator+file.getName();
                new ImageUtil().cutImage(test1, test2, 0, 30, 600, 600,"png");
            }
        }
    }
    /**
     * 将路径中图片变为缩略图
     */
    public static void shortImgs(String path,Integer height,Integer width) throws Exception {
        File startPath=new File(path);
            for (File file:startPath.listFiles()){
                String test1=path+File.separator+file.getName();
                new ImageUtil().thumbnailImage(test1, height, width);
            }
    }
    public static void showColor(String path)
    {
        try{
            BufferedImage bimg = ImageIO.read(new File(path));
            int [][] data = new int[bimg.getWidth()][bimg.getHeight()];
            System.out.println(bimg.getWidth() + "Df" + bimg.getHeight());
            Map<String,Integer> test=new HashMap<>();
            List<Map> blocks=new ArrayList<>();
            for(int i=0;i<bimg.getWidth();i++){
                String testimg="";
                for(int j=0; j <bimg.getHeight();j++) {
//                    data[i][j]=bimg.getRGB(i,j);
//                    testimg=data[i][j]+"";
//                    if (!test.containsKey(testimg)){
//                        test.put(testimg,1);
//                    }else {
//                        test.put(testimg,Integer.valueOf(test.get(testimg))+1);
//                    }
                    int color = 0xff000000;
                    BlackCheck(i, j, bimg, blocks, color, null);
                }
            }
            System.out.println(blocks.size());
            System.out.println(test);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * block指代色块,本方法将一个像素及周围所有的同色像素点作为一个块
     */
    public static void BlackCheck(int i,int j,BufferedImage bimg,List<Map> blocks,int color,Map<Integer,Integer> fromMap){
        int [][] data = new int[bimg.getWidth()][bimg.getHeight()];//图片像素矩阵
        data[i][j]=bimg.getRGB(i,j);
        if (data[i][j]!=color){//不符合颜色
            return;
        }
        for (Map map:blocks){//如果该点已存在于块中,直接返回
            if(map.containsKey(i)&&map.containsValue(j)){
                return;
            }
        }
        Map<Integer,Integer> block;
        if(fromMap==null){//如果没有母块
            block=new HashMap<>();//新建色块
            blocks.add(block);//放入色块列表
        }else {
            block=fromMap;
        }
        block.put(i,j);//装入像素点
        BlackCheck(i + 1, j, bimg, blocks, color, block);//递归验证
        BlackCheck(i,j+1,bimg,blocks,color,block);
        BlackCheck(i + 1,j+1,bimg,blocks,color,block);
    }
}
