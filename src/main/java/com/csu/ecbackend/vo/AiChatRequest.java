package com.csu.ecbackend.vo;

public class AiChatRequest {

    private String prompt;

    public AiChatRequest() {
    }

    public AiChatRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
