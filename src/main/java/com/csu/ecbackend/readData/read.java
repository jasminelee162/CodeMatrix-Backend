package com.csu.ecbackend.readData;


import java.io.*;
import java.util.Objects;

//采用该类读取数据并处理数据，然后存储
public class read {

    private String pathIN;

    private String pathOUT;

    private String state;

    public read(String pathIN, String pathOUT, String state) {
        this.pathIN = pathIN;
        this.pathOUT=pathOUT;
        this.state=state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void readData(){

        try{
            File file = new File(pathIN);
            FileInputStream inputStream=new FileInputStream(file);//字节输入流
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "GBK");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，

            File file1 =new File(pathOUT);
            //FileWriter fileWriter=new FileWriter(pathOUT);
            FileOutputStream outputStream=new FileOutputStream(file1);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream, "GBK");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);//为了提高写入的效率，使用了字符流的缓冲区.创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。

            if(state=="clean"){
                //初始化敏感词词库
                SensitiveWord sw = new SensitiveWord("CensorWords.txt");
                sw.InitializationWork();

                String s1=bufferedReader.readLine();
                while (s1 !=null){
                    /*System.out.println(s1);*/
                   // String[] s =bufferedReader.readLine().split("\\s+");
                    String[] s =s1.split("[\\s\\r]");
                 /*   System.out.println(s);*/
                    for(int i=4;i<s.length;i++){
                        //在这里加去掉敏感词
                        String str =s[i];
                        String str_clean_mgc=sw.filterInfo(str);
                        //含敏感词的记录去除，不含敏感词的记录写入txt
                        if(!str.equals(str_clean_mgc)){
                            //System.out.println("去除敏感词记录"+str);
                        }
                        else{
                            //System.out.println("字符串为:\n"+str);
                            bufferedWriter.write(s[i]);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                    }
                    s1=bufferedReader.readLine();
                }

            }
            else if(Objects.equals(state, "contain")){
                /*System.out.println(pathOUT);*/
                int loc1=pathOUT.lastIndexOf('n');
                loc1=loc1+1;
                int loc2=pathOUT.indexOf('.');
                String s1=bufferedReader.readLine();
                while (s1 !=null){
                    //System.out.println("test:"+s1);
                    //System.out.println(pathOUT.substring(loc1+1,loc2));
                    //System.out.println(s.contains(pathOUT.substring(loc1,loc2)));

                    goodContain gContain = new goodContain();

                    if(gContain.fcContain(s1,pathOUT.substring(loc1,loc2),"read")){
                        bufferedWriter.write(s1);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        // System.out.println(s[i]);
                    }
                    s1=bufferedReader.readLine();
                }
            }

            bufferedReader.close();
            bufferedWriter.close();

        }
        catch (FileNotFoundException | UnsupportedEncodingException e){
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        read re=new read("D:\\电子商务\\实验\\data\\电子商务与电子政务实验数据\\搜狗比赛数据\\user_tag_query.10W.TRAIN","D:\\电子商务\\实验\\data\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain.txt","contain");
//        re.readData();
////        Input in=new Input();
//
//    }

}
