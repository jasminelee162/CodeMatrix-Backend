package com.csu.ecbackend.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_record")
public class UserRecord {
    @TableId
    private int userid;
    private String record;
    private Date time;

    private String ip;

    public UserRecord() {
    }

    public UserRecord(int userid, String record, Date time, String ip) {
        this.userid = userid;
        this.record = record;
        this.time = time;
        this.ip = ip;
    }
}
