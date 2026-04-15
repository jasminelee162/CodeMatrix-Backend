package com.csu.ecbackend.readData;

import java.io.*;

public class ReadSa {
    private String inputPath;
    private String s;
    private String[] mediation;

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String[] getMediation() {
        return mediation;
    }

    public void setMediation(String[] mediation) {
        this.mediation = mediation;
    }

    public ReadSa(String inputPath, String s, String[] mediation) {
        this.inputPath = inputPath;
        this.s = s;
        this.mediation = mediation;
    }

    public int[] readSa(){
        try{
            int[] sa = new int[new ReadMediation().getNum()];

            for(int i=0;i<mediation.length;i++){
                File file = new File(inputPath);
                FileInputStream inputStream=new FileInputStream(file);//字节输入流
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "GBK");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，
                //System.out.println(mediation[i]);
                int count = 0;
                String s1=bufferedReader.readLine();
                while (s1 !=null){
                    if(s1.contains(s)&&s1.contains(mediation[i])){//
                        count++;
                    }
                    s1=bufferedReader.readLine();
                }
                sa[i]=count;
                //System.out.println(sa[i]);
                bufferedReader.close();
            }

            return sa;
        }
        catch (FileNotFoundException | UnsupportedEncodingException e){
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
