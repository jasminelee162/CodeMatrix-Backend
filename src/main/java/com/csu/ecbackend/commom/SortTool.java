package com.csu.ecbackend.commom;


import com.csu.ecbackend.bean.CompeteWord;

import java.util.Comparator;

public class SortTool implements Comparator {

      public int compare(Object o1, Object o2) {

            CompeteWord p1 = (CompeteWord) o1;
            CompeteWord p2 = (CompeteWord) o2;

            if (Double.parseDouble(p1.getCompete()) < Double.parseDouble(p2.getCompete()))
                  return 1;
            else if (Double.parseDouble(p1.getCompete()) == Double.parseDouble(p2.getCompete()))
                  return 0;
            else
                  return -1;
      }


}
