package com.csu.ecbackend.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedbackVO {
      private String keyword;

      private String compete;

      private List<DateAndTimes> dateAndTimes = new ArrayList<>();

}
