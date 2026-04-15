package com.csu.ecbackend.bean;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_info")
public class UserInfo {
      @TableId
      private int userid;
      private String password;
      private int permission;
}
