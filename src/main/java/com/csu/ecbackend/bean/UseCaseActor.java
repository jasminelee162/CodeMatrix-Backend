package com.csu.ecbackend.bean;

import lombok.Data;

import java.util.ArrayList;
@Data
public class UseCaseActor {
    private String actorName;
    private ArrayList<String> useCaseNameList;
}
