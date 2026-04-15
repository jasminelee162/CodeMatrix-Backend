package com.csu.ecbackend.service;

import com.csu.ecbackend.commom.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface UfcService {
    CommonResponse<String> getUfc(int[] s);
}
