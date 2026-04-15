package com.csu.ecbackend.readData;

import static java.lang.Thread.sleep;

public class TransFormToTxt implements Runnable {

    private read readData;
    private  String name;
    private  frequency fre;
    public  TransFormToTxt (read readData,String name,frequency fre){
        this.readData=readData;
        this.name=name;
        this.fre=fre;
    }


    public frequency getFre() {
        return fre;
    }

    public void setFre(frequency fre) {
        this.fre = fre;
    }

    public read getReadData() {
        return readData;
    }

    public void setReadData(read readData) {
        this.readData = readData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
       /* readData.readData();*/
        try {
            /*System.out.println("线程"+name+"开始运行");*/
            sleep((int) Math.random() * 10);
            /*readData.setPathIN(readData.getPathIN()+(i + 1));*/
            readData.readData();
            fre.getFrequency();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
