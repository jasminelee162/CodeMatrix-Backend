package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.VafService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@MapperScan("com.csu.ecbackend.persistence")
public class VafServiceImpl implements VafService {
    @Override
    public CommonResponse<String> getVaf(int[] s) {
        double res=0;
        for(int i=0;i<14;i++){
            res += s[i];
        }
        res = 0.65+0.01*res;
        return CommonResponse.createForSuccess("success",res+"");
    }
}
