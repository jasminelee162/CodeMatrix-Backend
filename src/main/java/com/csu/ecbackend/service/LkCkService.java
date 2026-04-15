package com.csu.ecbackend.service;

import com.csu.ecbackend.bean.*;
import com.csu.ecbackend.bean.Class;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.tzqServer.method.domain.MOODDTO;

import java.util.ArrayList;
import java.util.List;


public interface LkCkService {
    ArrayList<Class> getClassList(String url);
    ArrayList<Association> getAssociationList(String url);
    CommonResponse<ArrayList<LK>> getLk(String url);
    Class getParent(ArrayList<Class> classes,String id);
    CommonResponse<ArrayList<CK>>  getCk(String url);
//    boolean equalAtt(ArrayList<Attribute> attributesTemp,Attribute at);

}
