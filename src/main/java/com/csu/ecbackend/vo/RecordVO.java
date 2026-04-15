package com.csu.ecbackend.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecordVO {
      private  String keyword;

      private List<DateAndTimes> dateAndTimes =new ArrayList<>();

}
