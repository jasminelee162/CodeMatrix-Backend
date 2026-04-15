package com.csu.ecbackend.controller;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.tzqServer.method.MOODMethod;
import com.csu.ecbackend.tzqServer.method.domain.MOODDTO;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping("/mood")
public class moodController {
    @Autowired
    MOODMethod moodMethod;

    @PostMapping("/metric")
    @ResponseBody
    @ApiOperation("Mood")
    public CommonResponse<MOODDTO>getMoodMetric(@RequestParam("javaFiles") MultipartFile[] files) throws IOException {
        List<Path>pathList = new ArrayList<>();
        List<String>fileNames = new ArrayList<>();
        for (MultipartFile file:files){
            byte[] bytes = file.getBytes();
            String fileName  = "src/main/java/com/csu/ecbackend/file/"+ file.getOriginalFilename();

            fileNames.add(fileName);

            pathList.add(Paths.get(fileName));
            Files.write(pathList.get(pathList.size()-1), bytes);
        }

        CommonResponse<MOODDTO> MoodResult = moodMethod.doMetric(fileNames);
        return MoodResult;
    }
}
