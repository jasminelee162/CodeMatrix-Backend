package com.csu.ecbackend.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ASTClass {
    private String className;
    private ArrayList<ASTMethod> methodList;
    private int RFC;
    private int LCOM;
}