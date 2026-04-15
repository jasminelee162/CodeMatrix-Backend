package com.csu.ecbackend.controller;

import com.csu.ecbackend.service.LkCkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ed tan
 */
@Controller
public class FileController {


    @PostMapping("/uploadFile")
    @ResponseBody
    @CrossOrigin
    public String saveVue(String title,@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String fileName  = "src/main/java/com/csu/ecbackend/file/"+ file.getOriginalFilename();
        Path path = Paths.get(fileName);
        Files.write(path, bytes);
        System.out.println("SUCCESS");
        return "SUCCESS";
    }
}
