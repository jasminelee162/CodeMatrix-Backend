package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.CCService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Service
public class CCServiceImpl implements CCService {
    // 默认的圈复杂度
    private int complexity=1;
    public CommonResponse<Integer> getCCResult(String fileName){
        try {
            // 创建DOM解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // 创建DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 加载要解析的XML文件
//            String filePath = "C:\\Users\\Lenovo\\Desktop\\controlFlow.oom";
            File file = new File(fileName);

            // 解析XML文件并返回Document对象
            Document doc = builder.parse(file);

            // 获取<c:Decisions>元素
            NodeList nodeList = doc.getElementsByTagName("c:Decisions");


            // 获取<c:Decisions>元素下所有<o:Decision>元素
            Element cDecisions = (Element) nodeList.item(0);
            NodeList oDecisions = cDecisions.getElementsByTagName("o:Decision");

            complexity+=oDecisions.getLength();
//            System.out.println(complexity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResponse.createForSuccess("success",complexity);
    }
}
