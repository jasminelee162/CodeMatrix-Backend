package com.csu.ecbackend.readData;

import java.io.*;

public class ReadKa {
    private String pathIN;

    private String[] keyWord;

    private String[] mediation;

    public ReadKa(String pathIN, String[] keyWord, String[] mediation) {
        this.pathIN = pathIN;
        this.keyWord = keyWord;
        this.mediation = mediation;
    }

    public String getPathIN() {
        return pathIN;
    }

    public void setPathIN(String pathIN) {
        this.pathIN = pathIN;
    }

    public String[] getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String[] keyWord) {
        this.keyWord = keyWord;
    }

    public String[] getMediation() {
        return mediation;
    }

    public void setMediation(String[] mediation) {
        this.mediation = mediation;
    }

    public int[][] readKa() {

        try {
            File file = new File(pathIN);
            FileInputStream inputStream = new FileInputStream(file);//字节输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，

            int[][] kA = new int[keyWord.length][mediation.length];
            for(int x=0;x<keyWord.length;x++){
                for(int y=0;y<mediation.length;y++){
                    kA[x][y]=0;
                }
            }
            String s1 = bufferedReader.readLine();
            while (s1 != null) {
                for(int k=0;k<keyWord.length;k++){
                    if(s1.contains(keyWord[k])){
                        for(int a=0;a<mediation.length;a++){
                            if(s1.contains(mediation[a])){
                                kA[k][a]=kA[k][a]+1;
                            }
                        }
                    }
                }
                s1 = bufferedReader.readLine();
            }

            bufferedReader.close();
            return kA;

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
