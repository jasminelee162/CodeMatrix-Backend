package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.bean.*;
import com.csu.ecbackend.bean.Class;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.LkCkService;
import com.csu.ecbackend.tzqServer.method.domain.MOODDTO;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@MapperScan("com.csu.ecbackend.persistence")
public class LkCkServiceImpl implements LkCkService {

    @Override
    public ArrayList<Class> getClassList(String url){
        Document document = null;
        ArrayList<Class> classes = new ArrayList<Class>();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(url)); // 读取XML文件,获得document对象//D:\软件度量\web1\Electronic-Commerce\src\main\java\com\csu\ecbackend\test1.xml
            Element root = document.getRootElement();

            List elements = root.elements();

            for (Object element: elements){
                if(((Element) element).attribute(0).getValue().equals("uml:Class")){
                    Class classT = new Class();
                    classT.setId(((Element) element).attribute(1).getValue());
                    classT.setName(((Element) element).attribute(2).getValue());
                    List elementsSub =((Element) element).elements();
                    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                    ArrayList<Operation> operations = new ArrayList<Operation>();
                    for (Object elementSub: elementsSub){
                        if(((Element) elementSub).getName().equals("generalization")){
                            classT.setParent(((Element) elementSub).attribute("general").getValue());
//                            System.out.println(((Element) elementSub).attribute("general").getValue());
                        }
                        else if(((Element) elementSub).getName().equals("ownedAttribute")){
                            Attribute attribute = new Attribute();
//                            attribute.setId(((Element) elementSub).attribute("id").getValue());
                            attribute.setName(((Element) elementSub).attribute("name").getValue());
                            attribute.setVisibility(((Element) elementSub).attribute("visibility").getValue());
                            attribute.setType(((Element) elementSub).attribute("type").getValue());
//                            System.out.println(attribute.getId()+" "+attribute.getName()+" "+attribute.getVisibility()+" "+attribute.getType());
                            attributes.add(attribute);
                        }
                        else if(((Element) elementSub).getName().equals("ownedOperation")){
                            Operation operation = new Operation();
//                            operation.setId(((Element) elementSub).attribute("id").getValue());
                            operation.setName(((Element) elementSub).attribute("name").getValue());
                            operation.setVisibility(((Element) elementSub).attribute("visibility").getValue());
//                            System.out.println(operation.getId()+" "+operation.getName()+" "+operation.getVisibility());
                            List elementsSubSub =((Element) elementSub).elements();
                            ArrayList<Parameter> parameters = new ArrayList<Parameter>();
                            for (Object elementSubSub :elementsSubSub){
                                if(((Element) elementSubSub).getName().equals("ownedParameter")){
                                    Parameter parameter = new Parameter();
//                                    parameter.setId(((Element) elementSubSub).attribute("id").getValue());
                                    parameter.setName(((Element) elementSubSub).attribute("name").getValue());
                                    parameter.setType(((Element) elementSubSub).attribute("type").getValue());
                                    parameters.add(parameter);
                                }
                            }
                            operation.setParameters(parameters);
//                            System.out.println(operation.getId()+" "+operation.getName()+" "+operation.getVisibility()+" "+operation.getParameters().size());
                            operations.add(operation);
                        }
//                        System.out.println(((Element) elementSub).getName());
                        classT.setAttributes(attributes);
                        classT.setOperations(operations);
                    }
//                    System.out.println(classT.getId()+" "+classT.getName());
                    classes.add(classT);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return classes;
    }


    @Override
    public ArrayList<Association> getAssociationList(String url) {
        Document document = null;
        ArrayList<Association> associations =new ArrayList<Association>();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(url)); // 读取XML文件,获得document对象//D:\软件度量\web1\Electronic-Commerce\src\main\java\com\csu\ecbackend\test1.xml
            Element root = document.getRootElement();

            List elements = root.elements();

            for (Object element: elements){

                if(((Element) element).attribute(0).getValue().equals("uml:Association")){
                    Association association = new Association();
                    List elementsSub =((Element) element).elements();
                    int f=0;
                    for(Object elementSub: elementsSub){
                        if(f==0){
                            association.setBeginId(((Element) elementSub).attribute("type").getValue());
                            f=1;
                        }
                        else if(f==1){
                            association.setEndId(((Element) elementSub).attribute("type").getValue());
                        }
                    }
                    associations.add(association);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return associations;
    }

    @Override
    public CommonResponse<ArrayList<LK>> getLk(String url) {
        ArrayList<Class> classes = new ArrayList<Class>();
//        ArrayList<Association> associations =new ArrayList<Association>();
        classes = getClassList(url);
//        associations = getAssociationList(url);
        ArrayList<LK> lks = new ArrayList<LK>();

        double mtotal=0;
        for (Class c:classes){
            mtotal = mtotal + c.getOperations().size();
        }

        for(Class cl:classes){
            LK lk = new LK();
            lk.setClassName(cl.getName());
            int cs=0;
            if(cl.getAttributes()!=null&&cl.getOperations()!=null){
                cs = cs +cl.getAttributes().size()+cl.getOperations().size();
            }
            else{
                if(cl.getAttributes()!=null){
                    cs = cs +cl.getAttributes().size();
                }
                if(cl.getOperations()!=null){
                    cs = cs +cl.getOperations().size();
                }
            }
            Class parent = getParent(classes,cl.getParent());
            ArrayList<Attribute> attributesTemp = (ArrayList<Attribute>) cl.getAttributes().clone();
            ArrayList<Operation> operationsTemp = (ArrayList<Operation>) cl.getOperations().clone();
            int l=0;
            while(parent!=null){
                l++;
                ArrayList<Attribute> attributesTempTemp = (ArrayList<Attribute>) parent.getAttributes().clone();
                ArrayList<Operation> operationsTempTemp = (ArrayList<Operation>) parent.getOperations().clone();
                System.out.println(parent.getName()+" ");
                for(Attribute at:attributesTempTemp){
                    if((!attributesTemp.contains(at))&&("public".equals(at.getVisibility())|| "protected".equals(at.getVisibility()))){
                        attributesTemp.add(at);
                        cs ++;
                    }
                }
                for(Operation op:operationsTempTemp){
//                    System.out.println(!operationsTemp.contains(op));
                    if((!operationsTemp.contains(op))&&("public".equals(op.getVisibility())|| "protected".equals(op.getVisibility()))){
                        operationsTemp.add(op);
                        cs ++;
                    }
                }
                parent = getParent(classes, parent.getParent());
            }
            lk.setCs(cs);
            int noo=0,noa=0;
            if(getParent(classes,cl.getParent())!=null){
                Class parent1 = getParent(classes,cl.getParent());
                ArrayList<Operation> operationsTemp1 = cl.getOperations();
                ArrayList<Operation> operationsTemp2 = parent1.getOperations();
                for (Operation op: operationsTemp1){
                    if(operationsTemp2.contains(op)){
                        System.out.println(cl.getName()+" "+op.getName());
                        noo++;
                    }
                    else if(!operationsTemp2.contains(op)){
                        noa++;
                    }
                }
            }
            lk.setNoo(noo);
            lk.setNoa(noa);

            double si = (noo*l)/mtotal;
            lk.setSi(si);

//            if(cl.getOperations()!=null){
//                System.out.println(cl.getName()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////                for(Operation o: cl.getOperations()){
////                    System.out.println(o.getName());
////                }
//            }
//            System.out.println(cl.getName()+" "+noo+" "+" "+" "+mtotal+" "+l+" "+si);
            lks.add(lk);
        }
        return CommonResponse.createForSuccess("success",lks);
    }

    @Override
    public Class getParent(ArrayList<Class> classes, String id){
        for(Class cl:classes){
            if(cl.getId().equals(id)){
                return cl;
            }
        }
        return null;
    }

    @Override
    public CommonResponse<ArrayList<CK>> getCk(String url) {
        ArrayList<Class> classes = new ArrayList<Class>();
        ArrayList<Association> associations =new ArrayList<Association>();
        classes = getClassList(url);
        associations = getAssociationList(url);
        ArrayList<CK> cks = new ArrayList<CK>();
        for(Class cl:classes){
            CK ck = new CK();
            ck.setName(cl.getName());

            Class parent = getParent(classes,cl.getParent());

            int l=0;
            while(parent!=null){
                l++;
                parent = getParent(classes, parent.getParent());
            }
            ck.setDit(l);

            int noc=0;
            for(Class c:classes){
                if(c.getParent()!=null&&c.getParent().equals(cl.getId())){
                    noc++;
                }
            }
            ck.setNoc(noc);

            int cbo=0;
            for(Association association:associations){
                if(association.getBeginId().equals(cl.getId())){
                    cbo++;
                }
                if(association.getEndId().equals(cl.getId())){
                    cbo++;
                }
            }
            ck.setCbo(cbo);
            cks.add(ck);
        }
        return CommonResponse.createForSuccess("success",cks);
    }

//    @Override
//    public boolean equalAtt(ArrayList<Attribute> attributesTemp, Attribute at) {
//        for(Attribute attribute: attributesTemp){
//            if(at.getId().equals(attribute.getId()))
//        }
//        return false;
//    }


}
