package com.abc.doge.controller;

import com.abc.doge.dto.ChatRequestDto;
import com.abc.doge.dto.ChatResponseDto;
import com.abc.doge.service.AiChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiChatController {
    private final AiChatService aiChatService;

    public ApiChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat")
    public ChatResponseDto chat(@RequestBody ChatRequestDto chatRequestDto,
                                HttpSession session) {
        return aiChatService.callChatbotApi(chatRequestDto, session);
    }
}
