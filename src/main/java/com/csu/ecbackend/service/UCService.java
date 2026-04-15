package com.csu.ecbackend.service;
// 圈复杂度
import com.csu.ecbackend.bean.UseCaseFile;
import com.csu.ecbackend.commom.CommonResponse;

import java.util.Map;

public interface UCService {
    CommonResponse<UseCaseFile> getUCResult(String fileName);
}
