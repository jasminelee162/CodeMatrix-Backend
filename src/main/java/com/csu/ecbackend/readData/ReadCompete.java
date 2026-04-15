package com.csu.ecbackend.readData;


import com.csu.ecbackend.path.Path;
import com.csu.ecbackend.util.shieldContain;

import java.io.*;

public class ReadCompete {
    private String pathIN;

    private String pathOUT;

    private String[] mediation;

    public ReadCompete(String pathIN, String pathOUT, String[] mediation) {
        this.pathIN = pathIN;
        this.pathOUT = pathOUT;
        this.mediation = mediation;
    }

    public String getPathIN() {
        return pathIN;
    }

    public void setPathIN(String pathIN) {
        this.pathIN = pathIN;
    }

    public String getPathOUT() {
        return pathOUT;
    }

    public void setPathOUT(String path) {
        this.pathOUT = path;
    }

    public String[] getMediation() {
        return mediation;
    }

    public void setMediation(String[] mediation) {
        this.mediation = mediation;
    }

    public String[] readCompete() {

        try {
            File file = new File(pathIN);
            FileInputStream inputStream = new FileInputStream(file);//字节输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，

            File file1 = new File(pathOUT);
            //FileWriter fileWriter=new FileWriter(pathOUT);
            FileOutputStream outputStream = new FileOutputStream(file1);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "GBK");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);//为了提高写入的效率，使用了字符流的缓冲区.创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。

            String s1 = bufferedReader.readLine();
            int count=0;
            String[] keyWord=new String[new Path().getNumOfKey()];
            while (s1 != null&&count<new Path().getNumOfKey()) {
                boolean flag =true;
                for(int i=0;i<mediation.length;i++){
                    if(s1.contains(mediation[i])){
                        flag=false;
                        break;
                    }
                }
                String[] s = s1.split("\\s");
                //System.out.println(s[0]);
                boolean isContainShield= shieldContain.isContainShield(s[0]);
                //&&s[0].length()>1
                if(flag &&s[0].length()>1&&!isContainShield){
                    keyWord[count]=s[0];
                    count++;
                    bufferedWriter.write(s[0]);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                s1 = bufferedReader.readLine();
            }

            bufferedReader.close();
            bufferedWriter.close();
            return keyWord;

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
