package com.csu.ecbackend.readData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadLineCount {
    private String inputPath;

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public ReadLineCount(String inputPath) {
        this.inputPath = inputPath;
    }

    public long readLineCount() throws IOException {
        java.nio.file.Path path = Paths.get(inputPath);
        Charset cs = Charset.forName("GBK");
        return Files.lines(path, cs).count();
    }
}
