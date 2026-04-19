package com.csu.ecbackend.service;

import com.csu.ecbackend.vo.AiMetricReviewRequest;
import com.csu.ecbackend.vo.AiMetricReviewResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiUrl;
    private final String apiKey;

    public AiChatService(
            @Value("${ai.chat.api.url:https://api.deepseek.com/v1/chat/completions}") String apiUrl,
            @Value("${ai.chat.api.key:}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public String chat(String prompt) {
        if (!StringUtils.hasText(prompt)) {
            throw new IllegalArgumentException("Chat prompt cannot be empty.");
        }

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(createMessage("user", prompt));
        return chatWithMessages(messages, 0.7);
    }

    public AiMetricReviewResponse reviewMetrics(AiMetricReviewRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Metric review request cannot be empty.");
        }
        if (request.getMetrics() == null || request.getMetrics().isEmpty()) {
            throw new IllegalArgumentException("Metrics cannot be empty.");
        }

        String systemPrompt = "你是一名资深软件度量与架构评审顾问。请基于输入的指标，进行客观评价并给出可执行改进建议。"
                + "必须返回严格 JSON，字段包括：overallAssessment(字符串), riskLevel(字符串), keyFindings(字符串数组), suggestions(字符串数组)。";

        String userPrompt = buildMetricReviewPrompt(request);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(createMessage("system", systemPrompt));
        messages.add(createMessage("user", userPrompt));

        String answer = chatWithMessages(messages, 0.2);
        return parseMetricReviewResponse(answer);
    }

    private String chatWithMessages(List<Map<String, String>> messages, double temperature) {
        String key = StringUtils.hasText(this.apiKey) ? this.apiKey : System.getenv("AI_CHAT_API_KEY");
        if (!StringUtils.hasText(key)) {
            throw new IllegalStateException(
                    "AI chat API key is not configured. Set ai.chat.api.key or AI_CHAT_API_KEY.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(key);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "deepseek-chat");
        body.put("temperature", temperature);
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
                    String.class);
            String responseBody = responseEntity.getBody();

            if (!responseEntity.getStatusCode().is2xxSuccessful() || responseBody == null) {
                throw new RuntimeException(
                        "AI chat provider returned an error: " + responseEntity.getStatusCodeValue());
            }

            try {
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode contentNode = root.path("choices").path(0).path("message").path("content");
                if (contentNode.isMissingNode() || contentNode.isNull()) {
                    return root.path("error").path("message").asText("AI chat response is empty.");
                }
                return contentNode.asText();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse AI chat response.", e);
            }
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            if (StringUtils.hasText(responseBody) && (responseBody.contains("insufficient")
                    || responseBody.contains("balance") || responseBody.contains("quota"))) {
                return "抱歉，AI 服务当前不可用（账户余额不足或配额已用完）。这是小组作业演示，请节约使用或联系管理员。";
            }
            throw new RuntimeException(
                    "AI chat provider returned an error: " + e.getStatusCode() + " - " + responseBody, e);
        }
    }

    private Map<String, String> createMessage(String role, String content) {
        Map<String, String> message = new HashMap<>();
        message.put("role", role);
        message.put("content", content);
        return message;
    }

    private String buildMetricReviewPrompt(AiMetricReviewRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("请评估以下软件度量结果，并给出改进建议。\n");
        builder.append("项目名称: ").append(defaultText(request.getProjectName(), "未提供")).append("\n");
        builder.append("指标体系: ").append(defaultText(request.getMetricSystem(), "通用指标")).append("\n");
        builder.append("补充上下文: ").append(defaultText(request.getContext(), "无")).append("\n");
        builder.append("指标明细:\n");
        for (Map.Entry<String, Double> entry : request.getMetrics().entrySet()) {
            builder.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        builder.append("请同时给出总体评价、风险等级、关键发现和可执行建议。");
        return builder.toString();
    }

    private String defaultText(String value, String defaultValue) {
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    private AiMetricReviewResponse parseMetricReviewResponse(String answer) {
        AiMetricReviewResponse response = new AiMetricReviewResponse();
        response.setRawAnswer(answer);
        try {
            JsonNode root = objectMapper.readTree(answer);
            response.setOverallAssessment(root.path("overallAssessment").asText(""));
            response.setRiskLevel(root.path("riskLevel").asText(""));
            response.setKeyFindings(readStringArray(root.path("keyFindings")));
            response.setSuggestions(readStringArray(root.path("suggestions")));
            return response;
        } catch (Exception e) {
            response.setOverallAssessment(answer);
            response.setRiskLevel("未知");
            response.setKeyFindings(Collections.singletonList("AI 返回结果非标准 JSON，已保留原始文本。"));
            response.setSuggestions(Collections.singletonList("建议重试，或调整输入指标后再次评估。"));
            return response;
        }
    }

    private List<String> readStringArray(JsonNode node) {
        if (node == null || !node.isArray()) {
            return new ArrayList<>();
        }
        List<String> values = new ArrayList<>();
        for (JsonNode item : node) {
            values.add(item.asText());
        }
        return values;
    }
}
