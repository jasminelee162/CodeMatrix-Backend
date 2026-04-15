package com.csu.ecbackend.util;


import java.io.*;

public class shieldContain {
    public  static  boolean isContainShield(String keyWords) throws IOException {
        File file = new File("src/main/resources/txt/shield.txt");
        FileInputStream inputStream=new FileInputStream(file);//字节输入流
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "GBK");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，
        String s1=bufferedReader.readLine();
        boolean result=false;
        while (s1!=null){
            if (keyWords.contains(s1)){
                result=true;
                break;
            }else {
                s1=bufferedReader.readLine();
            }


        }


        return  result;
    }
}
