package com.csu.ecbackend.controller;

import com.csu.ecbackend.commom.CommonResponse;
import com.csu.ecbackend.service.AiChatService;
import com.csu.ecbackend.vo.AiChatRequest;
import com.csu.ecbackend.vo.AiChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/ai")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping("/chat")
    @ResponseBody
    public CommonResponse<AiChatResponse> chat(@RequestBody AiChatRequest request) {
        String answer = aiChatService.chat(request.getPrompt());
        AiChatResponse response = new AiChatResponse(answer);
        return CommonResponse.createForSuccess("success", response);
    }
}
