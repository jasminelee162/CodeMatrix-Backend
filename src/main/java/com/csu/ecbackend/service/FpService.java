package com.csu.ecbackend.service;

import com.csu.ecbackend.commom.CommonResponse;

public interface FpService {
    CommonResponse<String> getFp(int[] s1,int[] s2);
    CommonResponse<String> getFep(int[] s1);
}
