package com.csu.ecbackend.service;
// 圈复杂度
import com.csu.ecbackend.commom.CommonResponse;

public interface CCService {
    CommonResponse<Integer> getCCResult(String fileName);
}
