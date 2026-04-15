package com.csu.ecbackend.service.impl;

import com.csu.ecbackend.bean.UseCaseActor;
import com.csu.ecbackend.bean.UseCaseFile;
import com.csu.ecbackend.commom.CommonResponse;
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
import java.util.Map;

@Service
public class UCServiceImpl implements UCService {
    private int totalActorNum = 0;
    private int totalUseCaseNum = 0;
    @Override
    public CommonResponse<UseCaseFile> getUCResult(String fileName) {
        UseCaseFile useCaseFile = new UseCaseFile();
        try {
            // 创建DOM解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // 创建DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 加载要解析的XML文件
            File file = new File(fileName);

            // 解析XML文件并返回Document对象
            Document doc = builder.parse(file);

            // 获取执行者元素
            NodeList cAcotrs = doc.getElementsByTagName("c:Actors");
            // 获取<c:Actors>元素下所有<o:Actor>元素
            Element actors = (Element) cAcotrs.item(0);
            NodeList oActors = actors.getElementsByTagName("o:Actor");
            NodeList userNameList = actors.getElementsByTagName("a:Name");
            ArrayList<String> actorIdList = new ArrayList<>();
            Map<String,String> actorMap = new HashMap<>();
            for(int i=0;i<oActors.getLength();i++){
                Element element = (Element) oActors.item(i);
                String actorId = element.getAttribute("Id");
                actorMap.put(actorId,userNameList.item(i).getTextContent());
                actorIdList.add(actorId);
            }
            totalActorNum += oActors.getLength();

            // 获取用例元素
            NodeList allUseCase = doc.getElementsByTagName("c:UseCases");
            // 获取<c:UseCases>元素下所有<o:UseCase>元素
            Element useCases = (Element) allUseCase.item(0);
            NodeList oUseCases = useCases.getElementsByTagName("o:UseCase");
            NodeList useCaseNameList = useCases.getElementsByTagName("a:Name");
            ArrayList<String> useCaseIdList = new ArrayList<>();
            Map<String,String> useCaseMap = new HashMap<>();
            totalUseCaseNum+=useCaseNameList.getLength();
            for(int i=0;i<oUseCases.getLength();i++){
                Element useCaseElement = (Element) oUseCases.item(i);
                String useCaseId = useCaseElement.getAttribute("Id");
//                System.out.println(useCaseId);
                useCaseMap.put(useCaseId,useCaseNameList.item(i).getTextContent());
                useCaseIdList.add(useCaseId);
            }

            // 现在开始找每个执行者对应的用例名称
            // 获取依赖关系
            NodeList allLinks = doc.getElementsByTagName("c:ChildTraceabilityLinks");
            // 获取<c:ChildTraceabilityLinks>元素下所有<o:ExtendedDependency>元素
            Element cLinksElement = (Element) allLinks.item(0);
            NodeList cDependencyList = cLinksElement.getElementsByTagName("o:ExtendedDependency");
            ArrayList<UseCaseActor> useCaseActors = new ArrayList<>();
            for (int i=0;i<actorIdList.size();i++){
                UseCaseActor useCaseActor = new UseCaseActor();
                String now_id = actorIdList.get(i);
                useCaseActor.setActorName(actorMap.get(now_id));
                ArrayList<String> useCaseNames = new ArrayList<>();
                for(int j=0;j<cDependencyList.getLength();j++){
                    Element cDependency = (Element) cDependencyList.item(j);
                    // 获取<o:Actor>元素
                    Element actor = (Element) cDependency.getElementsByTagName("o:Actor").item(0);
//                    actorNode = (Element)actorNode.getElementsByTagName("o:Actor").item(0);
                    if (actor!=null){
                        String actorId = actor.getAttribute("Ref");
                        if(actorId.equals(now_id)){
                            Element useCaseNode = (Element)cDependency.getElementsByTagName("o:UseCase").item(0);
                            String useCaseId = useCaseNode.getAttribute("Ref");
                            String useCaseName = useCaseMap.get(useCaseId);
                            useCaseNames.add(useCaseName);
                        }
                    }
                }
                useCaseActor.setUseCaseNameList(useCaseNames);
                useCaseActors.add(useCaseActor);
            }
            useCaseFile.setTotalActorNum(totalActorNum);
            useCaseFile.setTotalUseCaseNum(totalUseCaseNum);
            useCaseFile.setUseCaseActors(useCaseActors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResponse.createForSuccess("success",useCaseFile);
    }

}
