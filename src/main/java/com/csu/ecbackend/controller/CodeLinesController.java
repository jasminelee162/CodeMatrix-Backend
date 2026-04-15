package com.csu.ecbackend.controller;


import com.csu.ecbackend.bean.CodeLines;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.CodeLinesService;
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
    public CommonResponse<CodeLines> codeLine(@RequestParam("javaFile") MultipartFile file ) throws IOException {  //@RequestParam String username, @RequestParam String keyword, @RequestParam String competeword, @RequestParam int evalute,HttpSession session

        byte[] bytes = file.getBytes();
        String fileName  = "src/main/java/com/csu/ecbackend/file/"+ file.getOriginalFilename();
        Path path = Paths.get(fileName);
        Files.write(path, bytes);
        CommonResponse<CodeLines> codeLineList = codeLinesService.getCodeLines(fileName);
        return codeLineList;
    }
}
