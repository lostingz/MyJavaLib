package com.mylib.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImageUtil {
    private static void DownloadImageByUrl(String httpUrl,String fileName,String filePath){
        try {
            URL url=new URL(httpUrl);
            URLConnection conn=url.openConnection();
            InputStream is=conn.getInputStream();
            File file=new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            OutputStream os=new FileOutputStream(filePath+"/"+fileName);
            byte[] bs=new byte[1024];
            int len=0;
            while((len=is.read(bs))!=-1){
                os.write(bs, 0, len);
            }
            is.close();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void downloadImage(String url,String param,String filePath){
        url = url+"/"+param;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Elements elements=doc.select("img");
        List<String> imageIdList=new ArrayList<String>();
        for (Element element : elements) {
            String src=element.attr("src");
            if(src.indexOf("http://")!=-1){
                imageIdList.add(src);
            }
        }
        int length=imageIdList.size();
        for (int i = 0; i < length; i++) {
            String imageUrl=imageIdList.get(i);
            String extention=".png";
            if(imageUrl.indexOf(".jpg")!=-1){
                extention=".jpg";
            }
            String fileName=String.valueOf(System.currentTimeMillis());
            ImageUtil.DownloadImageByUrl(imageUrl,fileName+extention,filePath);
            System.out.println(fileName+extention+" finished!");
        }
    }
}
