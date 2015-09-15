package com.test;


public class TestImage {
    public static void main(String[] args) {
       String url="http://www.chsi.com.cn";
       int pageCount=20;
       for (int i = 1; i < pageCount; i++) {
           ImageUtil.downloadImage(url,"","e:/testImage/test");
       }
       System.out.println("download finished!");
    }
    
}