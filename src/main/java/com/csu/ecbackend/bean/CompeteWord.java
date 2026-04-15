package com.csu.ecbackend.bean;


import lombok.Data;

@Data
public class CompeteWord {



  private String id;
  private String keyWord;
  private String competeWord;
  private String compete;

  private int good=0;

  private int bad=0;

  public CompeteWord(String id, String keyWord, String competeWord, String compete, int good, int bad) {
    this.id = id;
    this.keyWord = keyWord;
    this.competeWord = competeWord;
    this.compete = compete;
    this.good = good;
    this.bad = bad;
  }

  public CompeteWord(String id, String keyWord, String competeWord, String compete) {

    this.id = id;
    this.keyWord = keyWord;
    this.competeWord = competeWord;
    this.compete = compete;
  }
}
