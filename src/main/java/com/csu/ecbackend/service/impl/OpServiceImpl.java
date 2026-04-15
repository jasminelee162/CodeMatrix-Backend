package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.OpService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@MapperScan("com.csu.ecbackend.persistence")
public class OpServiceImpl implements OpService {
    @Override
    public CommonResponse<String> getOp(int[] s,double r,double pro) {
        double res=0;
        res = s[0] +2*s[1]+3*s[2]+2*s[3]+5*s[4]+8*s[5]+10*s[6];
        res =res * ( 1 - r );
        res = res/pro;
        return CommonResponse.createForSuccess("success",res+"");
    }

}
