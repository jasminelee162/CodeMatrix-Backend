package com.csu.ecbackend.service;

import com.csu.ecbackend.commom.CommonResponse;

public interface VafService {
    CommonResponse<String> getVaf(int[] s);
}
