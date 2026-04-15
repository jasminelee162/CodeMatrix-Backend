package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.bean.StoryPoint;
import com.csu.ecbackend.bean.UseCaseActor;
import com.csu.ecbackend.bean.UseCaseFile;
import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.StoryPointService;
import com.csu.ecbackend.service.UCService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoryPointServiceImpl implements StoryPointService {
    @Override
    public CommonResponse<List<StoryPoint>> getSPResult(String fileName) {
        List<StoryPoint> storyPointList = new ArrayList<>();
        ArrayList<String> useCaseNames = new ArrayList<>();
        try {

            // 创建DOM解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // 创建DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 加载要解析的XML文件
            File file = new File(fileName);

            // 解析XML文件并返回Document对象
            Document doc = builder.parse(file);

            // 获取用例元素
            NodeList allUseCase = doc.getElementsByTagName("c:UseCases");
            // 获取<c:UseCases>元素下所有<o:UseCase>元素
            Element useCases = (Element) allUseCase.item(0);
            NodeList oUseCases = useCases.getElementsByTagName("o:UseCase");
            NodeList useCaseNameList = useCases.getElementsByTagName("a:Name");
            for(int i=0;i<oUseCases.getLength();i++){
                useCaseNames.add(useCaseNameList.item(i).getTextContent());
                StoryPoint storyPoint = new StoryPoint();
                storyPoint.setFunctionPoint(useCaseNameList.item(i).getTextContent());
                storyPoint.setStory_point(3);
                storyPoint.setId(i+1);
                storyPoint.setShow(false);
                storyPointList.add(storyPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResponse.createForSuccess("success",storyPointList);
    }

}
