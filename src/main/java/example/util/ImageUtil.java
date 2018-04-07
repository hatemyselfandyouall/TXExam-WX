package example.util;

/**
 * Created by YS-GZD-1495 on 2017/11/15.
 */

import java.io.*;


import java.awt.Color;
        import java.awt.Graphics;
        import java.awt.Image;
        import java.awt.image.BufferedImage;
        import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
        import javax.imageio.ImageReadParam;
        import javax.imageio.ImageReader;
        import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;


/**
 * <p>Title: ImageUtil </p>
 * <p>Description: </p>
 * <p>Email: icerainsoft@hotmail.com </p>
 * @author Ares
 * @date 2014年10月28日 上午10:24:26
 */
public class ImageUtil {

    static {
//        Slf4jUtil.buildSlf4jUtil().loadSlf4j();
    }
    private Logger log = LoggerFactory.getLogger(getClass());

    private static String DEFAULT_THUMB_PREVFIX = "thumb_";
    private static String DEFAULT_CUT_PREVFIX = "cut_";
    private static Boolean DEFAULT_FORCE = false;
    private static final String JPG_HEX = "ff";
    private static final String PNG_HEX = "89";
    private static final String JPG_EXT = "jpg";
    private static final String PNG_EXT = "png";

    /**
     * <p>Title: cutImage</p>
     * <p>Description:  根据原图与裁切size截取局部图片</p>
     * @param srcImg    源图片
     * @param output    图片输出流
     * @param rect        需要截取部分的坐标和大小
     */
    public void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect,String proType){
        if(srcImg.exists()){
            java.io.FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = proType;
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fis != null) fis.close();
                    if(iis != null) iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            log.warn("the src image is not exist.");
        }
    }

    public void cutImage(File srcImg, OutputStream output, int x, int y, int width, int height,String proType){
        cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height),proType);
    }

    public void cutImage(File srcImg, String destImgPath, java.awt.Rectangle rect,String proType){

        File destImg = new File(destImgPath);
        if(destImg.exists()){
        }else {
            try {
                destImg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            cutImage(srcImg, new java.io.FileOutputStream(destImgPath), rect,proType);
        } catch (FileNotFoundException e) {
            log.warn("the dest image is not exist.");
        }
    }

    public void cutImage(File srcImg, String destImg, int x, int y, int width, int height,String proType){
        cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height),proType);
    }

    public void cutImage(String srcImg, String destImg, int x, int y, int width, int height,String proType){
        cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width, height),proType);
    }
    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public void thumbnailImage(File srcImg, OutputStream output, int w, int h, String prevfix, boolean force){
        if(srcImg.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                log.debug("target image's size, width:{}, height:{}.",w,h);
                Image img = ImageIO.read(srcImg);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                if(!force){
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            log.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            log.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, output);
                output.close();
            } catch (IOException e) {
                log.error("generate thumbnail image failed.",e);
            }
        }else{
            log.warn("the src image is not exist.");
        }
    }
    public void thumbnailImage(File srcImg, int w, int h, String prevfix, boolean force){
        String p = srcImg.getAbsolutePath();
        try {
            if(!srcImg.isDirectory()) p = srcImg.getParent();
            if(!p.endsWith(File.separator)) p = p + File.separator;
            thumbnailImage(srcImg, new java.io.FileOutputStream(p + prevfix +srcImg.getName()), w, h, prevfix, force);
        } catch (FileNotFoundException e) {
            log.error("the dest image is not exist.",e);
        }
    }

    public void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force){
        File srcImg = new File(imagePath);
        thumbnailImage(srcImg, w, h, prevfix, force);
    }

    public void thumbnailImage(String imagePath, int w, int h, boolean force){
        thumbnailImage(imagePath, w, h, DEFAULT_THUMB_PREVFIX, DEFAULT_FORCE);
    }

    public void thumbnailImage(String imagePath, int w, int h){
        thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
    }

    /**
     * 获取页面展示用Base64码前缀
     * @param imgUrl
     * @return
     */
    public static String getImgPrefix(String imgUrl){
        String[] temp=imgUrl.split("\\.");
        String type=temp[temp.length-1];
        String prefix="data:image/"+type+";base64,";
        return prefix;
    }

    /**
     * img路径获取图片转换为base64码
     */
    public static String ImgUrlToBase64(String url){
        File file=new File(url);
        byte[] buffer;
        try {
            InputStream in=new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            in.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
        return b64.encodeAsString(buffer);
    }

    public static String getFileExt(String filePath) {
        FileInputStream fis = null;
        String extension = FilenameUtils.getExtension(filePath);
        try {
            fis = new FileInputStream(new File(filePath));
            byte[] bs = new byte[1];
            fis.read(bs);
            String type = Integer.toHexString(bs[0]&0xFF);
            if(JPG_HEX.equals(type))
                extension = JPG_EXT;
            if(PNG_HEX.equals(type))
                extension = PNG_EXT;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return extension;
    }

    public static void main(String[] args) {
        new ImageUtil().thumbnailImage("imgs/Tulips.jpg", 150, 100);
//        new ImageUtil().cutImage("E:\\test1.png","E:\\test1.png", 250, 70, 300, 400,"jpg");
    }

}