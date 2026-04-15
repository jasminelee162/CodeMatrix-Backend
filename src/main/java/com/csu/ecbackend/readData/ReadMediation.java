package com.csu.ecbackend.readData;


import com.csu.ecbackend.dao.CompeteDao;
import com.csu.ecbackend.dao.MediateDao;
import com.csu.ecbackend.path.Path;
import com.csu.ecbackend.util.StringUtils;
import com.csu.ecbackend.util.shieldContain;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReadMediation {
    private String pathIN;

    private final int num=15;

    public ReadMediation(String pathIN) {
        this.pathIN = pathIN;
    }

    public String getPathIN() {
        return pathIN;
    }

    public void setPathIN(String pathIN) {
        this.pathIN = pathIN;
    }

    public int getNum() {
        return num;
    }

    public ReadMediation() {
    }

    public String[] readMediation(String word){

        try{
            File file = new File(pathIN);
            FileInputStream inputStream=new FileInputStream(file);//字节输入流
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, StandardCharsets.UTF_8);//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，

            String[] s= new String[num];
            int count=0;
            bufferedReader.readLine();
            while (count<num){
                String s1 = bufferedReader.readLine();
                if(s1!=null){
                    System.out.println(s1);
                    String[] s2 =s1.split("[\\s\\r]");
                    s[count]=s2[0];
                    String s12=s[count];
                    boolean isContainShield= shieldContain.isContainShield(s[count]);
                    //s[count].length()>1&&
                    if(s[count].length()>1&&!isContainShield){
                        count++;
                    }
                }
            }
            bufferedReader.close();

            MediateDao mediateDao = new MediateDao();
            String firstLetter = StringUtils.getFirstLetter(word);
            int k = firstLetter.charAt(0) - 'A';
            if (mediateDao.checkWordExist(word, k + 1)) {
                System.out.println("关键词的中介词" + word + "已存在，取消插入");
                return s;
            }
            for (int i = 0; i < new Path().getNumOfKey(); i++) {
                if (i < 15) {
                    /*String s = StringUtils.getFirstLetter(word);
                    int k=s.charAt(0)-'A';*/
                    mediateDao.addmediate_word(word, s[i], 1, k + 1);
                }
            }
            mediateDao.addIndex(word, k + 1);
            return s;
        }
        catch (FileNotFoundException | UnsupportedEncodingException e){
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
