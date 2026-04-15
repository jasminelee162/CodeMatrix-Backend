package com.csu.ecbackend.vo;

import com.csu.ecbackend.bean.CompeteWord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Data
public class KeywordVO {
      private String keyword;

      private List<CompeteWord> lists;

      public KeywordVO(String keyword, List<CompeteWord> lists) {
            this.keyword = keyword;
            this.lists = lists;
      }
      public KeywordVO(String keyword, String row) {
            this.keyword = keyword;
            lists = new ArrayList<>();
            String[] split = row.split("\n");
            for (String s : split) {
                  String[] s1 = s.split(" ");
                  lists.add(new CompeteWord(s1[0], keyword, s1[2], s1[3]));
            }
      }
}
