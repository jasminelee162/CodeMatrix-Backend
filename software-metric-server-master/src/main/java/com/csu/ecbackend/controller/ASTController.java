package com.csu.ecbackend.controller;


import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.ASTService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/AST")
//@Api(value = "代码行相关接口", tags = "代码行")
public class ASTController {

    @Autowired
    private ASTService astService;

    @PostMapping("/ASTControl")
    @ResponseBody
    @ApiOperation("ASTControl")
    public CommonResponse<Map> getASTResult(@RequestParam String fileName){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
//        System.out.println(fileName);
//        CommonResponse<List<Integer>> codeLineList = codeLinesService.getCodeLines(fileName);
        CommonResponse<Map> ASTResultMap = astService.getASTResult(fileName);
        return ASTResultMap;
    }
    @PostMapping("/test")
    @ResponseBody
    @ApiOperation("test")
    public CommonResponse<String> test(@RequestParam String fileName){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

        return CommonResponse.createForSuccess("success",fileName);
    }
}
