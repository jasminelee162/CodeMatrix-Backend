package com.csu.ecbackend.compute;


import com.csu.ecbackend.dao.CompeteDao;
import com.csu.ecbackend.path.Path;
import com.csu.ecbackend.readData.ReadMediation;
import com.csu.ecbackend.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class computeComp {

      private String pathOUT;

      private float[] w;

      private int[][] kA;

      private int[] a;

      private int[] sa;

      private String[] keyWord;

      public String getPathOUT() {
            return pathOUT;
      }

      public void setPathOUT(String path) {
            this.pathOUT = path;
      }

      public float[] getW() {
            return w;
      }

      public void setW(float[] w) {
            this.w = w;
      }

      public int[][] getkA() {
            return kA;
      }

      public void setkA(int[][] kA) {
            this.kA = kA;
      }

      public int[] getA() {
            return a;
      }

      public void setA(int[] a) {
            this.a = a;
      }

      public int[] getSa() {
            return sa;
      }

      public void setSa(int[] sa) {
            this.sa = sa;
      }

      public String[] getKeyWord() {
            return keyWord;
      }

      public void setKeyWord(String[] keyWord) {
            this.keyWord = keyWord;
      }

      public computeComp(String pathOUT, float[] w, int[][] kA, int[] a, int[] sa, String[] keyWord) {
            this.pathOUT = pathOUT;
            this.w = w;
            this.kA = kA;
            this.a = a;
            this.sa = sa;
            this.keyWord = keyWord;
      }

      public void computeComp(String word) {
            List<Float> resultList = new ArrayList<>();


            File file1 = new File(pathOUT);


            //FileWriter fileWriter=new FileWriter(pathOUT);
            /*FileOutputStream outputStream = new FileOutputStream(file1);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "GBK");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);*///为了提高写入的效率，使用了字符流的缓冲区.创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。

            for (int i = 0; i < new Path().getNumOfKey(); i++) {
                  float result = 0;
                  for (int j = 0; j < new ReadMediation().getNum(); j++) {
                        int temp = a[j] - sa[j];
                        if (temp == 0) {
                              temp = 999999999;
                        }
                        float comp = (float) kA[i][j] / (temp);
                        result = result + w[j] * comp;
                  }
                  resultList.add(result);
                  //System.out.println("result"+result);
               /* bufferedWriter.write("Comp("+keyWord[i]+"):"+result);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/
            }
            /*   bufferedWriter.close();*/
        /* catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("error！");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
            for (int i = 0; i < resultList.size() - 1; i++) { //控制循环次数，比长度少一次。
                  for (int j = 0; j < resultList.size() - i - 1; j++) { //后面排好的值不需要进行比较，所以减去i。
                        if (resultList.get(j) < resultList.get(j + 1)) {
                              Float temp = resultList.get(j);
                              resultList.set(j, resultList.get(j + 1));
                              resultList.set(j + 1, temp);
                              String temp_keyword = keyWord[j];
                              keyWord[j] = keyWord[j + 1];
                              keyWord[j + 1] = temp_keyword;
                        }
                  }

            }

            try {
                  FileOutputStream outputStream = new FileOutputStream(file1);
                  OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "GBK");
                  BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);//
                  CompeteDao competeDao = new CompeteDao();

                  String s = StringUtils.getFirstLetter(word);
                  int k = s.charAt(0) - 'A';
                  if (competeDao.checkWordExist(word, k + 1)) {
                        System.out.println("关键词" + word + "已存在，取消插入");
                        return;
                  }
                  for (int i = 0; i < new Path().getNumOfKey(); i++) {
                        if (i < 15) {
                    /*String s = StringUtils.getFirstLetter(word);
                    int k=s.charAt(0)-'A';*/
                              competeDao.addCompete_word(word, keyWord[i], resultList.get(i), k + 1);
                        }

                        bufferedWriter.write("Comp(" + keyWord[i] + "):" + resultList.get(i));
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                  }
                  competeDao.addIndex(word, k + 1);
                  bufferedWriter.close();
            } catch (FileNotFoundException e) {
                  e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }
}
