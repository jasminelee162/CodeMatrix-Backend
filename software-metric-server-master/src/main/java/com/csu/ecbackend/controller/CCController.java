package com.csu.ecbackend.controller;


import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.CCService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/CircleComplexity")
//@Api(value = "代码行相关接口", tags = "代码行")
public class CCController {

    @Autowired
    private CCService ccService;

    @PostMapping("/circleComplexity")
    @ResponseBody
    @ApiOperation("circleComplexity")
    public CommonResponse<Integer> codeLine(@RequestParam String fileName ){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session
//        System.out.println(fileName);
        CommonResponse<Integer> complexity = ccService.getCCResult(fileName);
        return complexity;
    }
}
