package com.csu.ecbackend.readData;

import static java.lang.Thread.sleep;

public class TransFormToTxt2 implements Runnable {
    private read readData;
    private  String name;

    public  TransFormToTxt2 (read readData,String name){
        this.readData=readData;
        this.name=name;

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
            sleep((int) Math.random() * 1);
            /*readData.setPathIN(readData.getPathIN()+(i + 1));*/
            readData.readData();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
