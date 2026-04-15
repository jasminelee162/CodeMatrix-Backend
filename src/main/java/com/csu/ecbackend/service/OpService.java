package com.csu.ecbackend.service;

import com.csu.ecbackend.commom.CommonResponse;

public interface OpService {
    CommonResponse<String> getOp(int[] s,double r,double pro);
}
