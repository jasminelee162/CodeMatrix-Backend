package com.csu.ecbackend.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Class {
    private String id=null;
    private String name=null;
    private String parent = null;
    private ArrayList<Attribute> attributes=new ArrayList<Attribute>();
    private ArrayList<Operation> operations=new ArrayList<Operation>();
}
