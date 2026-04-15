package com.csu.ecbackend.controller;

import com.csu.ecbackend.bean.LK;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.LkCkService;
import com.csu.ecbackend.service.UfcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/lk")
@Api(value = "lk接口", tags = "lk")
public class LkController {
    @Autowired
    LkCkService lkCkService;

    @PostMapping("/lk")
    @ResponseBody
    @ApiOperation("lk")
    //@RequestParam("oomFile") MultipartFile file
    public CommonResponse<ArrayList<LK>> lk(@RequestParam String name) throws IOException {  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session
//        byte[] bytes = file.getBytes();
        String fileName  = "src/main/java/com/csu/ecbackend/file/"+ name;
//        Path path = Paths.get(fileName);
//        Files.write(path, bytes);
        return lkCkService.getLk(fileName);
    }
}
