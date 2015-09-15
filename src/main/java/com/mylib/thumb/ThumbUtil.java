package com.mylib.thumb;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGEncodeParam;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/** 
 * 创建缩略图 
 * @author   
 * @date    2010-6-19 
 * @file    org.demo.common.ImageThumbnail.java 
 */  
public class ThumbUtil {  
    /** 
     * @param args 
     */  
    public static void main(String[] args)throws Exception {  
        String inFilename = "e:\\Temp\\screen.png";  
        String outFilename = "e:\\Temp\\demo2.png";  
        createThumbnail(inFilename, outFilename, 800, 600, 0.75f);  
    }  
    /** 
     * 创建缩略图 
     * @param inFilename 
     * @param outFilename     
     * @param thumbWidth 
     * @param thumbHeight 
     * @param quality 
     *        [0.0,1.0] 
     * @return 
     */  
    public static void createThumbnail(  
            String inFilename,String outFilename,  
            int thumbWidth,int thumbHeight,float quality)  
            throws IOException,InterruptedException{  
        // 加载图像  
        Image image = Toolkit.getDefaultToolkit().getImage(inFilename);  
        // 创建图像追踪器  
        MediaTracker mediaTracker = new MediaTracker(new Container());  
        mediaTracker.addImage(image, 0);  
        /** !必须等图像完全加载到内存之后才能执行后续操作! */  
        mediaTracker.waitForID(0);  
        System.out.println("isErrorAny=" + mediaTracker.isErrorAny());  
        // 计算等比例缩放的实际 宽高值  
        double thumbRatio = thumbWidth/thumbHeight;  
        int imageWidth = image.getWidth(null);  
        int imageHeight = image.getHeight(null);  
        double imageRatio = (double)imageWidth/imageHeight;  
        if (thumbRatio < imageRatio){  
            // --> height  
            thumbHeight = (int)(thumbWidth/imageRatio);  
        } else {  
            // --> width  
            thumbWidth = (int)(thumbHeight * imageRatio);  
        }  
        // 创建内存缩略图  
        BufferedImage bufferedImage = new BufferedImage(thumbWidth,   
                                                        thumbHeight,   
                                                        BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2d = bufferedImage.createGraphics();  
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                             RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g2d.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);  
        // 将内存缩略图 写入 文件  
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);  
        param.setQuality(quality,false);  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(bufferedImage);  
        out.close();  
    }  
}  