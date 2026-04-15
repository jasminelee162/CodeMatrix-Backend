package com.csu.ecbackend.readData;

import java.io.*;
import java.util.*;

public class frequency {

      private String pathIN;

      private String pathOUT;

      private String state;

      private String all;

      public frequency(String pathIN, String pathOUT) {
            this.pathIN = pathIN;
            this.pathOUT = pathOUT;
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

      public static void countWordOccurrence(File file) throws IOException {
            Map<String, Integer> map = new TreeMap<>(); //树映射表
            try (Scanner input = new Scanner(file)) {
                  while (input.hasNextLine()) {
                        String text = input.nextLine();
                        String[] words = text.split("\n");

                        for (int i = 0; i < words.length; i++) {
                              String key = words[i].trim();

                              //添加单词
                              if (key.length() > 0) {
                                    if (!map.containsKey(key))
                                          map.put(key, 1);
                                    else
                                          map.put(key, map.get(key) + 1);
                              }
                        }
                  }

                  Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
                  for (Map.Entry<String, Integer> entry : entrySet)    //输出单词和数目
                        System.out.println(String.format("%-10d%10s", entry.getValue(), entry.getKey()));
            }
      }


      public void getFrequency() {
            try {
                  File file = new File(pathIN);
                  FileInputStream inputStream = new FileInputStream(file);//字节输入流
                  InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");//将字节流变成字符流，同时确认编码格式为GBK或UTF-8
                  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//将字符流放到字符流缓冲区中，

                  File file1 = new File(pathOUT);
                  FileOutputStream outputStream = new FileOutputStream(file1);
                  OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                  BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);//为了提高写入的效率，使用了字符流的缓冲区.创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。


                  Map<String, Integer> map = new TreeMap<>(); //树映射表
                  // System.out.println(pathIN);
                  //String s1=bufferedReader.readLine();
                  String s = bufferedReader.readLine();
                  while (s != null) {
                        String[] words = s.split(" ");

                        for (int i = 0; i < words.length; i++) {
                              String key = words[i].trim();
                              //添加进Map
                              if (key.length() > 0) {
                                    if (!map.containsKey(key))
                                          map.put(key, 1);
                                    else
                                          map.put(key, map.get(key) + 1);
                              }
                        }
                        s = bufferedReader.readLine();
                  }

                  ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

                  Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {

                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                              // 按从大到小的顺序排列
                              return o2.getValue() - o1.getValue();
                              // 按从小到大的顺序排列
                              //return o2.getValue() - o1.getValue();
                        }
                  });
                  //遍历list，这会儿我们已经把map放进到list中了
                  for (Map.Entry entry:list) {
                        System.out.println( entry.getKey()+" " +entry.getValue());
                        bufferedWriter.write(entry.getKey()+" " +entry.getValue().toString());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                  }

//                  Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
//                  for (Map.Entry<String, Integer> entry : entrySet)    //输出单词和数目
//                  {
//                        System.out.println( entry.getKey()+" " +entry.getValue());
//                        bufferedWriter.write(entry.getKey()+" " +entry.getValue().toString());
//                        bufferedWriter.newLine();
//                        bufferedWriter.flush();
//                  }
                  bufferedReader.close();
                  bufferedWriter.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                  System.out.println("error！");
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      //demo
    public static void main(String[] args) {
            String path1="D:\\电子商务\\实验\\data\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsCleanbyNLP2.txt";
            String path2="D:\\电子商务\\实验\\data\\电子商务与电子政务实验数据\\搜狗比赛数据\\cs1.txt";
          frequency frequency = new frequency(path1, path2);
          frequency.getFrequency();
    }
}
