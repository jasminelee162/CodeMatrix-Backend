package com.csu.ecbackend;

import com.csu.ecbackend.bean.*;
import com.csu.ecbackend.bean.Class;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {

    // private static final String[] words
    // ={"华为","宝马","斗破苍穹","面膜","乐视","周杰伦","月饼","长沙","苹果","钢琴","杨洋","淘宝","韩国","教师","微信"};
    // private static final String[] words ={"华为","韩国"};
    // public static void main(String[] args) throws IOException,
    // InterruptedException {
    //// int count=5;//拆分文件数量
    //// String filrPath=new Path().getYuanPathOrigin();//原始数据
    //// String cleanPath=new Path().getYuanPathClean1();//清洗数据
    //// long startTime = System.currentTimeMillis();
    //// System.out.println("开始拆分原始数据文件，将原始数据分为"+count+"个部分");
    //// test_nio.splitFile(filrPath,5);
    //// long endTime = System.currentTimeMillis();
    //// System.out.println("拆分原始数据耗费时间： " + (endTime - startTime) + " ms");
    ////
    //// //数据清洗
    //// System.out.println("开始数据清洗");
    //// long startTime_clean= System.currentTimeMillis();
    //// List<Thread> threadList = new ArrayList<>();
    //// for (int i=0;i<count;i++){
    ////
    //// read readData_clean=new
    // read(filrPath+(i+1),cleanPath+(i+1)+".txt","clean");
    //// TransFormToTxt2 transFormToTxt2=new TransFormToTxt2(readData_clean,i+1+"");
    //// Thread thread_clean= new Thread(transFormToTxt2);
    //// threadList.add(thread_clean);
    //// thread_clean.start();
    ////
    //// }
    //// for (int i=0;i<count;i++){
    //// threadList.get(i).join();
    //// }
    //// List<File> files = new ArrayList<File>();
    //// for(int i=0;i<count;i++) {
    //// files.add(new File(cleanPath+(i+1)+".txt"));
    //// }
    ////
    //// test_nio.merge(files, cleanPath+".txt");
    ////
    //// long endTime_clean= System.currentTimeMillis();
    //// System.out.println("数据清洗用时"+(endTime_clean-startTime_clean)+"ms");
    ////
    ////
    ////
    //
    // //种子关键词计算contain和correlation
    // System.out.println("开始计算相关词");
    // long startTime_CONTAIN_CORRELATION=System.currentTimeMillis();
    // List<Thread> threadList1 = new ArrayList<>();
    // for(int i=0;i<words.length;i++){
    // /*read re1=new
    // read("F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsClean.txt","F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain"+words[i]+".txt","contain");
    // re1.readData();
    // frequency fre =new
    // frequency("F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\wordsContain"+words[i]+".txt","F:\\电子商务应用\\电子商务实验\\数据\\电子商务与电子政务实验数据\\搜狗比赛数据\\correlation"+words[i]+".txt");
    // fre.getFrequency();*/
    //
    // read readData_contain=new read(new Path().getYuanPathClean(),new
    // Path().getYuanPathContain()+words[i]+".txt","contain");
    // //System.out.println("test11111111111");
    // frequency fre_correlation =new frequency(new
    // Path().getYuanPathContain()+words[i]+".txt",new
    // Path().getYuanPathCorrelation()+words[i]+".txt");
    //
    // TransFormToTxt transFormToTxt=new
    // TransFormToTxt(readData_contain,i+1+"",fre_correlation);
    //
    // Thread thread_fre= new Thread(transFormToTxt);
    // threadList1.add(thread_fre);
    // thread_fre.start();
    // }
    // for (int i=0;i<threadList1.size();i++){
    // threadList1.get(i).join();
    // }
    // long endTime_CONTAIN_CORRELATION=System.currentTimeMillis();
    // System.out.println("计算相关词用时"+(endTime_CONTAIN_CORRELATION-startTime_CONTAIN_CORRELATION)+"ms");
    // //Input in=new Input();
    //
    //
    // for(int i=0;i<words.length;i++){
    // ReadMediation readMediation= new ReadMediation(new
    // Path().getYuanPathCorrelation()+words[i]+".txt");
    // String[] mediation= readMediation.readMediation(words[i]);
    //
    //// System.out.println("S: "+words[i]);
    //// for(int y=0;y<mediation.length;y++){
    //// System.out.println(mediation[y]);
    //// }
    //// System.out.println(" ");
    //// System.out.println(" ");
    //// System.out.println(" ");
    ////// if(i==words.length-1) {
    ////// for (int y = 0; y < mediation.length; y++) {
    ////// System.out.println(new String(mediation[y].getBytes("gbk"),
    // StandardCharsets.UTF_8));
    ////// }
    ////// }
    // long sCount= new ReadLineCount(new Path().getYuanPathContain() + words[i] +
    // ".txt").readLineCount();
    // int[] sa = new ReadSa(new Path().getYuanPathContain() + words[i] +
    // ".txt",words[i],mediation).readSa();
    // float[] w = new float[new ReadMediation().getNum()];
    // for(int j=0;j<w.length;j++){
    // w[j]=((float)sa[j])/sCount;
    // //System.out.println(w[j]);
    // }
    // ReadContainMediation readContainMediation = new ReadContainMediation(new
    // Path().getYuanPathClean(),new Path().getYuanPathContain() +"Mediation"+
    // words[i] + ".txt",mediation);
    // int[] a=readContainMediation.readContainMediation();
    // frequency frequency = new frequency(new Path().getYuanPathContain()
    // +"Mediation"+ words[i] + ".txt",new Path().getYuanPathSelectKey()+ words[i] +
    // ".txt");
    // frequency.getFrequency();
    // ReadCompete readCompete = new ReadCompete(new Path().getYuanPathSelectKey()+
    // words[i] + ".txt",new Path().getYuanPathKey()+ words[i] + ".txt",mediation);
    // String[] keyWord=readCompete.readCompete();
    // ReadKa readKa=new ReadKa(new Path().getYuanPathContain() +"Mediation"+
    // words[i] + ".txt",keyWord,mediation);
    // int[][] kA=readKa.readKa();
    //// for(int x=0;x<keyWord.length;x++){
    //// for(int y=0;y<mediation.length;y++){
    //// System.out.print(kA[x][y]+" ");
    //// }
    //// System.out.println(" ");
    //// }
    // computeComp computeComp = new computeComp(new Path().getYuanPathComp()+
    // words[i] + ".txt",w,kA,a,sa,keyWord);
    // computeComp.computeComp(words[i]);
    // }
    //
    //
    //
    //
    // }

    public static void main(String[] args) throws IOException, InterruptedException {
        Document document = null;
        ArrayList<Class> classes = new ArrayList<Class>();
        ArrayList<Association> associations = new ArrayList<Association>();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(
                    new File("D:\\软件度量\\web1\\Electronic-Commerce\\src\\main\\java\\com\\csu\\ecbackend\\test1.xml")); // 读取XML文件,获得document对象
            Element root = document.getRootElement();

            List elements = root.elements();

            for (Object element : elements) {
                if (((Element) element).attribute(0).getValue().equals("uml:Class")) {
                    Class classT = new Class();
                    classT.setId(((Element) element).attribute(1).getValue());
                    classT.setName(((Element) element).attribute(2).getValue());
                    List elementsSub = ((Element) element).elements();
                    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                    ArrayList<Operation> operations = new ArrayList<Operation>();
                    for (Object elementSub : elementsSub) {
                        if (((Element) elementSub).getName().equals("generalization")) {
                            classT.setParent(((Element) elementSub).attribute("general").getValue());
                            // System.out.println(((Element) elementSub).attribute("general").getValue());
                        } else if (((Element) elementSub).getName().equals("ownedAttribute")) {
                            Attribute attribute = new Attribute();
                            // attribute.setId(((Element) elementSub).attribute("id").getValue());
                            attribute.setName(((Element) elementSub).attribute("name").getValue());
                            attribute.setVisibility(((Element) elementSub).attribute("visibility").getValue());
                            attribute.setType(((Element) elementSub).attribute("type").getValue());
                            // System.out.println(attribute.getId()+" "+attribute.getName()+"
                            // "+attribute.getVisibility()+" "+attribute.getType());
                            attributes.add(attribute);
                        } else if (((Element) elementSub).getName().equals("ownedOperation")) {
                            Operation operation = new Operation();
                            // operation.setId(((Element) elementSub).attribute("id").getValue());
                            operation.setName(((Element) elementSub).attribute("name").getValue());
                            operation.setVisibility(((Element) elementSub).attribute("visibility").getValue());
                            // System.out.println(operation.getId()+" "+operation.getName()+"
                            // "+operation.getVisibility());
                            List elementsSubSub = ((Element) elementSub).elements();
                            ArrayList<Parameter> parameters = new ArrayList<Parameter>();
                            for (Object elementSubSub : elementsSubSub) {
                                if (((Element) elementSubSub).getName().equals("ownedParameter")) {
                                    Parameter parameter = new Parameter();
                                    // parameter.setId(((Element) elementSubSub).attribute("id").getValue());
                                    parameter.setName(((Element) elementSubSub).attribute("name").getValue());
                                    parameter.setType(((Element) elementSubSub).attribute("type").getValue());
                                    parameters.add(parameter);
                                }
                            }
                            operation.setParameters(parameters);
                            // System.out.println(operation.getId()+" "+operation.getName()+"
                            // "+operation.getVisibility()+" "+operation.getParameters().size());
                            operations.add(operation);
                        }
                        // System.out.println(((Element) elementSub).getName());
                        classT.setAttributes(attributes);
                        classT.setOperations(operations);
                    }
                    // System.out.println(classT.getId()+" "+classT.getName());
                    classes.add(classT);
                } else if (((Element) element).attribute(0).getValue().equals("uml:Association")) {
                    Association association = new Association();
                    List elementsSub = ((Element) element).elements();
                    int f = 0;
                    for (Object elementSub : elementsSub) {
                        if (f == 0) {
                            association.setBeginId(((Element) elementSub).attribute("type").getValue());
                            f = 1;
                        } else if (f == 1) {
                            association.setEndId(((Element) elementSub).attribute("type").getValue());
                        }
                    }
                    associations.add(association);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
