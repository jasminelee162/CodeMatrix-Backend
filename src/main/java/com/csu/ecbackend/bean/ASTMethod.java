package com.csu.ecbackend.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ASTMethod {
    private String methodName;
    private String accessAuthority;
    private String returnType;
    private ArrayList<String> parameters;
    private ArrayList<String> OutMethodNameList;

}
