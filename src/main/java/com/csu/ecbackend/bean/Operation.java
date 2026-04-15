package com.csu.ecbackend.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Operation {
//    private String id;
    private String name=null;
    private String visibility=null;
    private String returnValue=null;
    ArrayList<Parameter> parameters=new ArrayList<Parameter>();
}
