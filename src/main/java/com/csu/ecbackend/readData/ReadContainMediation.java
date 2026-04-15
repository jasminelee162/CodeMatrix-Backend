package com.csu.ecbackend.readData;

import java.io.*;
import java.util.Arrays;

public class ReadContainMediation {
    private String pathIN;

    private String pathOUT;

    private String[] mediation;


    public ReadContainMediation(String pathIN, String pathOUT, String[] mediation) {
        this.pathIN = pathIN;
        this.pathOUT=pathOUT;
        this.mediation=mediation;
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

    public int[] readContainMediation() {

        try {
            File file = new File(pathIN);
            FileInputStream inputStream = new FileInputStream(file);//字节输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，

            File file1 = new File(pathOUT);
            //FileWriter fileWriter=new FileWriter(pathOUT);
            FileOutputStream outputStream = new FileOutputStream(file1);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "GBK");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);//为了提高写入的效率，使用了字符流的缓冲区.创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。

            /*System.out.println(pathOUT);*/
            int loc1 = pathOUT.lastIndexOf('n');
            loc1 = loc1 + 1;
            int loc2 = pathOUT.indexOf('.');
            String s1 = bufferedReader.readLine();
            int[] a = new int[mediation.length];
            Arrays.fill(a, 0);
            while (s1 != null) {
                for (int i = 0; i < mediation.length; i++) {
                    if (s1.contains(mediation[i])) {
                        a[i] = a[i]+1;
                    }
                }
                //System.out.println("test:"+s1);
                //System.out.println(pathOUT.substring(loc1+1,loc2));
                //System.out.println(s.contains(pathOUT.substring(loc1,loc2)));
                boolean flag = false;
                //System.out.println(s1);
                for (int i = 0; i < mediation.length; i++) {
                    if (s1.contains(mediation[i])) {
                        flag = true;
                        break;
                    }
                }
                //System.out.println(pathOUT.substring(loc1, loc2));
                //System.out.println(s1);
                //System.out.println(flag);
                if ((!s1.contains(pathOUT.substring(loc1, loc2))) && flag) {
                    bufferedWriter.write(s1);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    // System.out.println(s[i]);
                }
                s1 = bufferedReader.readLine();
            }

            bufferedReader.close();
            bufferedWriter.close();
            return a;

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
