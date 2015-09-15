package com.mylib.screen;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 截屏
 * @author zhenggm<a href="mailto:zhenggm@chsi.com.cn">zhenggm</a>
 * @version $Id$
 */
public class ScreenShot {
    public static void main(String[] args)throws Exception {  
        String fileName = "E:\\Temp\\screen.png";
        File file=new File(fileName);
        if(!file.exists()){
            file.mkdirs();
        }
        // 获取屏幕尺寸  
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();  
        Rectangle rectangle = new Rectangle(dimension);  
        // 创建图像  
        BufferedImage image = new Robot().createScreenCapture(rectangle);  
        // 保存到磁盘   
        // @see ImageIO#getWriterFormatNames();  
        ImageIO.write(image, "png", file);  
    }  
}
