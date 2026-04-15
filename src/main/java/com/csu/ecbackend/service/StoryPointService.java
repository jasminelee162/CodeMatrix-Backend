package com.csu.ecbackend.service;
// 圈复杂度
import com.csu.ecbackend.bean.StoryPoint;
import com.csu.ecbackend.commom.CommonResponse;

import java.util.List;

public interface StoryPointService {
    CommonResponse<List<StoryPoint>> getSPResult(String fileName);
}
