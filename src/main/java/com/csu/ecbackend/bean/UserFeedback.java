package com.csu.ecbackend.bean;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_feedback")
public class UserFeedback {

  @TableId
  private int userid;
  private String keyword;
  private String competeword;
  private int evaluate;
  private Date time;

  public UserFeedback(int userid, String keyword, String competeword, int evaluate, Date time) {
    this.userid = userid;
    this.keyword = keyword;
    this.competeword = competeword;
    this.evaluate = evaluate;
    this.time = time;
  }
}
