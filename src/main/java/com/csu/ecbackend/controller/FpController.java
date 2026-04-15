package com.csu.ecbackend.controller;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.FpService;
import com.csu.ecbackend.service.UfcService;
import com.csu.ecbackend.service.VafService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping("/fp")
@Api(value = "fp接口", tags = "fp")
public class FpController {

    @Autowired
    UfcService ufcService;

    @Autowired
    VafService vafService;

    @Autowired
    FpService fpService;

    @PostMapping("/ufc")
    @ResponseBody
    @ApiOperation("ufc")
    public CommonResponse<String> ufc(@RequestParam int[] s){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
        CommonResponse<String> ufc = ufcService.getUfc(s);
        return ufc;
    }

    @PostMapping("/vaf")
    @ResponseBody
    @ApiOperation("vaf")
    public CommonResponse<String> vaf(@RequestParam int[] s){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
        CommonResponse<String> vaf = vafService.getVaf(s);
        return vaf;
    }

    @PostMapping("/fp")
    @ResponseBody
    @ApiOperation("fp")
    public CommonResponse<String> fp(@RequestParam int[] s1,@RequestParam int[] s2){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
        CommonResponse<String> fp = fpService.getFp(s1,s2);
        return fp;
    }

    @PostMapping("/fep")
    @ResponseBody
    @ApiOperation("fep")
    public CommonResponse<String> fep(@RequestParam int[] s1){  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

//        int[] s = (int[]) session.getAttribute("ufc");
        CommonResponse<String> fep = fpService.getFep(s1);
        return fep;
    }

}
