package com.csu.ecbackend.controller;

import com.csu.ecbackend.commom.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;

@Controller
@CrossOrigin
@RequestMapping("/ai")
public class AiOptimizeController {

    @Value("${ai.chat.api.url}")
    private String apiUrl;

    @Value("${ai.chat.api.key}")
    private String apiKey;

    private static final String SEPARATOR = "===EXPLANATION===";

    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(30))
            .setReadTimeout(Duration.ofSeconds(120))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/optimize")
    @ResponseBody
    public CommonResponse<Map<String, String>> optimize(@RequestBody Map<String, String> body) {
        String code = body.getOrDefault("code", "");
        if (code == null || code.trim().isEmpty()) {
            return CommonResponse.createForError("代码内容不能为空");
        }

        String prompt = "你是一个面向对象软件质量优化专家。下面是一个 UML XMI 格式的 XML 文件，描述了类结构。\n"
                + "请根据 CK 度量标准（WMC、RFC、DIT、NOC、LCOM、CBO）对其进行优化，目标是：\n"
                + "- 降低耦合度（CBO）：减少不必要的关联\n"
                + "- 减少方法缺乏内聚度（LCOM）：让方法使用更多共同参数\n"
                + "- 控制继承深度（DIT）：避免过深的继承链\n\n"
                + "【重要约束】：\n"
                + "1. 所有元素的 xmi:id 属性值必须保持不变，不能修改或替换\n"
                + "2. generalization 的 general 属性、ownedAttribute 的 type 属性必须引用已存在的 xmi:id\n"
                + "3. 输出必须是合法的 XML，根元素和命名空间声明保持不变\n"
                + "4. 不要添加任何 XML 注释\n\n"
                + "请严格按照以下格式输出，不要偏离：\n"
                + "第一部分：优化后的完整 XML 内容\n"
                + "第二部分：在单独一行输出分隔符 " + SEPARATOR + "\n"
                + "第三部分：用中文逐条说明你做了哪些优化以及原因\n\n"
                + "原始 XML：\n" + code;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> msgMap = new LinkedHashMap<>();
            msgMap.put("role", "user");
            msgMap.put("content", prompt);

            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("messages", Collections.singletonList(msgMap));
            requestBody.put("temperature", 0.2);

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String fullContent = (String) message.get("content");

            String optimizedCode;
            String explanation;
            int sepIdx = fullContent.indexOf(SEPARATOR);
            if (sepIdx >= 0) {
                optimizedCode = stripMarkdownFence(fullContent.substring(0, sepIdx).trim());
                explanation   = fullContent.substring(sepIdx + SEPARATOR.length()).trim();
            } else {
                optimizedCode = stripMarkdownFence(fullContent.trim());
                explanation   = "";
            }

            Map<String, String> result = new LinkedHashMap<>();
            result.put("code", optimizedCode);
            result.put("explanation", explanation);
            System.out.println("=== AI optimizedCode (first 300 chars) ===");
            System.out.println(optimizedCode.length() > 300 ? optimizedCode.substring(0, 300) : optimizedCode);
            System.out.println("=== END ===");
            return CommonResponse.createForSuccess(result);
        } catch (Exception e) {
            return CommonResponse.createForError("AI 优化失败：" + e.getMessage());
        }
    }

    private String stripMarkdownFence(String text) {
        String s = text.trim();
        if (s.startsWith("```")) {
            int firstNewline = s.indexOf('\n');
            if (firstNewline >= 0) s = s.substring(firstNewline + 1);
            if (s.endsWith("```")) s = s.substring(0, s.lastIndexOf("```")).trim();
        }
        // 去掉 AI 在 XML 前面多输出的说明文字，找到第一个 < 开始的位置
        int xmlStart = s.indexOf("<?xml");
        if (xmlStart < 0) xmlStart = s.indexOf("<uml:");
        if (xmlStart < 0) xmlStart = s.indexOf("<");
        if (xmlStart > 0) s = s.substring(xmlStart);
        return s.trim();
    }
}
