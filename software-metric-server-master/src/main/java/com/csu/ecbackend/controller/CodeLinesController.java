package com.csu.ecbackend.controller;


import com.alibaba.fastjson.JSONObject;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.CodeLinesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/CodeLines")
//@Api(value = "代码行相关接口", tags = "代码行")
public class CodeLinesController {

    @Autowired
    private CodeLinesService codeLinesService;

    @PostMapping("/codeLine")
    @ResponseBody
    @ApiOperation("codeLine")
    public CommonResponse<List<Integer>> codeLine(@RequestParam String fileName ){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
//        System.out.println(fileName);
//        CommonResponse<List<Integer>> codeLineList = codeLinesService.getCodeLines(fileName);
        CommonResponse<List<Integer>> codeLineList = codeLinesService.getCodeLines(fileName);
        return codeLineList;
    }
}
