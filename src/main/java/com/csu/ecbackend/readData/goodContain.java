package com.csu.ecbackend.readData;

import java.io.IOException;

public class goodContain {
    private String s1;
    private String containWord;
    private String logFileName;

    public goodContain() {
    }

    public goodContain(String s1, String containWord, String logFileName) {
        this.s1 = s1;
        this.containWord = containWord;
        this.logFileName = logFileName;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getContainWord() {
        return containWord;
    }

    public void setContainWord(String containWord) {
        this.containWord = containWord;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public boolean fcContain(String s1, String containWord,String logFileName) throws IOException {
        boolean flag = false;

        //创建文件
//        File consoleFile = new File(logFileName+"goodContainLog2.txt");
//        if(!consoleFile.exists()) {
//            consoleFile.createNewFile();
//        }
//        FileWriter fw = new FileWriter(logFileName+"goodContainLog2.txt", true);
//        PrintWriter pw = new PrintWriter(fw);


        if(s1.contains(containWord)){
            //进行简单分词，将分词后的结果存入List中
            //List<Word> words = WordSegmenter.segWithStopWords(s1.toString());
            String[] words = s1.split(" ");

            int num = words.length;
            for(int i=0;i<num;i++){
                if(containWord.equals(words[i])){
                    flag = true;

                    break;
                }
            }
//            if(!flag){
//                pw.println("切分句子："+s1.toString());
//                pw.flush();
////                pw.println("切分结果："+words.toString());
////                pw.flush();
//                pw.println("不写入文件");
//                pw.flush();
//            }

        }



        return flag;
    }

}
