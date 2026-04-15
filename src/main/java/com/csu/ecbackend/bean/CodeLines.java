package com.csu.ecbackend.bean;

import lombok.Data;

@Data
public class CodeLines {
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
}
