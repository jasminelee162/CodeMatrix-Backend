package com.csu.ecbackend.service;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
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
        body.put("temperature", 0.7);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        messages.add(message);
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
            // 检查是否是余额不足或其他API错误
            String responseBody = e.getResponseBodyAsString();
            if (responseBody != null && (responseBody.contains("insufficient") || responseBody.contains("balance")
                    || responseBody.contains("quota"))) {
                return "抱歉，AI 服务当前不可用（账户余额不足或配额用完）。这是小组作业演示，请节约使用或联系管理员。";
            }
            throw new RuntimeException(
                    "AI chat provider returned an error: " + e.getStatusCode() + " - " + responseBody, e);
        }
    }
}
