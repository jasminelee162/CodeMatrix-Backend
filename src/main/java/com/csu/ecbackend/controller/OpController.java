package com.csu.ecbackend.controller;

import com.csu.ecbackend.bean.CK;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.LkCkService;
import com.csu.ecbackend.service.OpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/op")
@Api(value = "op接口", tags = "op")
public class OpController {
    @Autowired
    OpService opService;

    @PostMapping("/op")
    @ResponseBody
    @ApiOperation("op")
    public CommonResponse<String> op(@RequestParam int[] s,@RequestParam double r,@RequestParam double pro){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
        CommonResponse<String> op = opService.getOp(s,r,pro);
        return op;
    }

}
