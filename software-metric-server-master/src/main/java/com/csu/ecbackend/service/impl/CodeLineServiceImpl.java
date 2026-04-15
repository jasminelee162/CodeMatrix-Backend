package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.CodeLinesService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class CodeLineServiceImpl implements CodeLinesService {
    // 空行
    private int nullLines = 0;
    // 注释行
    private int annoLines = 0;
    // 非注释代码行
    private int codeLines = 0;
    // 总行,也即物理代码行
    private int allLines = 0;
    // 逻辑代码行
    private int logicLines = 0;
    public CommonResponse<List<Integer>> getCodeLines(String fileName){
        int count = 0;
        boolean multiLineComment = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines++;
                line = line.trim(); // 去除行首行尾的空格和制表符
                if (line.isEmpty()) {
                    nullLines++;
                    continue;
                }
                if (line.startsWith("//")) {
                    annoLines++;
                    continue; // 单行注释不计入逻辑行数
                }
                if (line.startsWith("/*")) {
                    annoLines++;
                    multiLineComment = true; // 进入多行注释模式
                    continue;
                }
                if (line.endsWith("*/")) {
                    multiLineComment = false; // 退出多行注释模式
                    annoLines++;
                    continue;
                }
                if(multiLineComment){
                    annoLines++;
                    continue;
                }
                codeLines++;
                Matcher m= Pattern.compile(".*[a-zA-Z]+.*").matcher(line);
                if (m.matches()) {
                    logicLines++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> codeLinesList= new ArrayList<>() ;
        codeLinesList.add(nullLines);
        codeLinesList.add(annoLines);
        codeLinesList.add(codeLines);
        codeLinesList.add(allLines);
        codeLinesList.add(logicLines);
        return CommonResponse.createForSuccess("success",codeLinesList);
    }
}
