package com.csu.ecbackend.commom;


import com.csu.ecbackend.bean.CompeteWord;

import java.util.Comparator;

public class SortTool1 implements Comparator {

      public int compare(Object o1, Object o2) {

            CompeteWord p1 = (CompeteWord) o1;
            CompeteWord p2 = (CompeteWord) o2;

            if (p1.getGood() < p2.getGood())
                  return 1;
            else if (p1.getGood() == p2.getGood())
                  return 0;
            else
                  return -1;
      }

}
