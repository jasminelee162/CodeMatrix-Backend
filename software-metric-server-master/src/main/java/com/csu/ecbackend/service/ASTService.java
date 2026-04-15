package com.csu.ecbackend.service;

import com.csu.ecbackend.commom.CommonResponse;

import java.util.List;
import java.util.Map;

public interface ASTService {
    // codeLines的组成结构是[空行、注释代码行、非注释代码行、物理代码行（总行）、逻辑代码行
//    CommonResponse<List<Integer>> getCodeLines(String fileName);
    CommonResponse<Map> getASTResult(String fileName);
}
