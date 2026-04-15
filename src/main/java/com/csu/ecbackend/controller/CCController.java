package com.csu.ecbackend.controller;


import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.CCService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public CommonResponse<Integer> codeLine(@RequestParam("oomFile") MultipartFile file ) throws IOException {  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

        byte[] bytes = file.getBytes();
        String fileName  = "src/main/java/com/csu/ecbackend/file/"+ file.getOriginalFilename();
        Path path = Paths.get(fileName);
        Files.write(path, bytes);
        CommonResponse<Integer> complexity = ccService.getCCResult(fileName);
        return complexity;
    }
}
