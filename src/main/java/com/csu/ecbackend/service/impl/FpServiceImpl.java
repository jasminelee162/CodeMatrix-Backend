package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.FpService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@MapperScan("com.csu.ecbackend.persistence")
public class FpServiceImpl implements FpService {

    private final int[] w = {3,4,6,4,5,7,3,4,6,5,7,10,7,10,15};

    @Override
    public CommonResponse<String> getFp(int[] s1, int[] s2) {
        int res1=0;
        for(int i=0;i<15;i++){
            res1 += w[i]*s1[i];
        }

        double res2=0;
        for(int i=0;i<14;i++){
            res2 += s2[i];
        }
        res2 = 0.65+0.01*res2;

        double res=0;
        res = res1 * res2;
        return CommonResponse.createForSuccess("success",res+"");
    }

    @Override
    public CommonResponse<String> getFep(int[] s1) {
        double res=0;
        res = 4*s1[0]+5*s1[1]+4*s1[2]+7*s1[3]+7*s1[4]+3*s1[5]; //6个特征的个数分别乘以这些权重
        //res2 = 0.65+0.01*res2;

        return CommonResponse.createForSuccess("success",res+"");
    }
}
