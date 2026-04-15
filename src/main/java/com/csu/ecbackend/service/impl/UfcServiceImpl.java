package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.UfcService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@MapperScan("com.csu.ecbackend.persistence")
public class UfcServiceImpl implements UfcService {

    private final int[] w = {3,4,6,4,5,7,3,4,6,5,7,10,7,10,15};

    @Override
    public CommonResponse<String> getUfc(int[] s) {
        int res=0;
        for(int i=0;i<15;i++){
            res += w[i]*s[i];
        }
        return CommonResponse.createForSuccess("success",res+"");
    }
}
