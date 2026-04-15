package com.csu.ecbackend.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UseCaseFile {
    private int totalActorNum = 0;
    private int totalUseCaseNum = 0;
    ArrayList<UseCaseActor> useCaseActors;
}
