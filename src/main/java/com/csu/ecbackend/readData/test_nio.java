package com.csu.ecbackend.readData;


import com.csu.ecbackend.util.shieldContain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class test_nio {
    public static void splitFile(String filePath, int fileCount) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        long average = fileSize / fileCount;//平均值
        long bufferSize = 100; //缓存块大小，自行调整
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + "")); // 申请一个缓存区
        long startPosition = 0; //子文件开始位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        if (b == 10 || b == 13) { //判断\n\r
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            }else{
                endPosition = fileSize; //最后一个文件直接指向文件末尾
            }

            FileOutputStream fos = new FileOutputStream(filePath + (i + 1));
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
            outputChannel.close();
            fos.close();
            startPosition = endPosition + 1;
            endPosition += average;
        }
        inputChannel.close();
        fis.close();

    }
    public static void merge(List<File> files , String to) {
        File t = new File(to);
        FileInputStream in = null;
        FileChannel inChannel = null;

        FileOutputStream out = null;
        FileChannel outChannel = null;
        try {
            out = new FileOutputStream(t, true);
            outChannel = out.getChannel();
            // 记录新文件最后一个数据的位置
            long start = 0;
            for (File file : files) {
                in = new FileInputStream(file);
                inChannel = in.getChannel();
                // 从inChannel中读取file.length()长度的数据，写入outChannel的start处
                outChannel.transferFrom(inChannel, start, file.length());
                start += file.length();
                in.close();
                inChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                outChannel.close();
            } catch (Exception e2) {
            }
        }
    }



    public static void main(String[] args) throws Exception {
        /*Scanner scanner = new Scanner(System.in);
        scanner.nextLine();*/
        long startTime = System.currentTimeMillis();
      /*  splitFile("F:\\电子商务应用\\电子商务实验\\数据\\user_tag_query.10W.TRAIN",5);
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
        String filrPath="F:\\电子商务应用\\电子商务实验\\数据\\user_tag_query.10W.TRAIN";*/

       System.out.println(shieldContain.isContainShield("2017"));

        //拆分文件成功

      /*  for (int i=0;i<5;i++){

            read readData=new read(filrPath+(i+1),"F:\\电子商务应用\\电子商务实验\\数据\\wordsClean"+(i+1)+".txt","clean");
            TransFormToTxt2 transFormToTxt2=new TransFormToTxt2(readData,i+1+"");
            new Thread(transFormToTxt2).start();

        }*/



    /*   String words[] ={"华为","宝马","斗破苍穹","周杰伦","月饼","长沙","苹果","杨洋","淘宝","韩国","钢琴","教师","微信","乐视","面膜"};
        for(int i=0;i<words.length;i++){
            *//*read re1=new read("F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsClean.txt","F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain"+words[i]+".txt","contain");
            re1.readData();
            frequency fre =new frequency("F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain"+words[i]+".txt","F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\correlation"+words[i]+".txt");
            fre.getFrequency();*//*
            read readData=new read("F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsClean.txt","F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain"+words[i]+".txt","contain");

            frequency fre =new frequency("F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain"+words[i]+".txt","F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\correlation"+words[i]+".txt");

            TransFormToTxt transFormToTxt=new TransFormToTxt(readData,i+1+"",fre);
            new Thread(transFormToTxt).start();
        }
*/
        //read readData=new read(filrPath+(1),"F:\\电子商务应用\\电子商务实验\\数据\\wordsClean"+(1)+".txt","clean");

       /* for (int i=0;i<5;i++){
            read readData=new read(filrPath+(i+1),"F:\\电子商务应用\\电子商务实验\\数据\\wordsClean"+(i+1)+".txt","clean");
            TransFormToTxt transFormToTxt=new TransFormToTxt(readData,i+1+"");
            new Thread(transFormToTxt).start();
        }*/

        /*scanner.nextLine();*/
    }

}
