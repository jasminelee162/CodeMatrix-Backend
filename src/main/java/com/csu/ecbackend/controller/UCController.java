package com.csu.ecbackend.controller;

// 计算用例图
import com.csu.ecbackend.bean.UseCaseFile;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.UCService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/UseCase")
//@Api(value = "代码行相关接口", tags = "代码行")
public class UCController {

    @Autowired
    private UCService ucService;

    @PostMapping("/useCase")
    @ResponseBody
    @ApiOperation("useCase")
    public CommonResponse<UseCaseFile> getUCResult(@RequestParam("oomFile") MultipartFile file ) throws IOException {  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

        //拿到具体文件 file
        byte[] bytes = file.getBytes();
        String fileName  = "src/main/java/com/csu/ecbackend/file/"+ file.getOriginalFilename();
        Path path = Paths.get(fileName);
        Files.write(path, bytes);
        CommonResponse<UseCaseFile> result = ucService.getUCResult(fileName);
        System.out.println("SUCCESS");
        return result;

    }
}
