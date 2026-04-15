package com.csu.ecbackend.commom;


import com.csu.ecbackend.bean.CompeteWord;

import java.util.Comparator;

public class SortTool2 implements Comparator {

      public int compare(Object o1, Object o2) {

            CompeteWord p1 = (CompeteWord) o1;
            CompeteWord p2 = (CompeteWord) o2;

            if (p1.getBad() > p2.getBad())
                  return 1;
            else if (p1.getBad() == p2.getBad())
                  return 0;
            else
                  return -1;
      }

}
