package com.wangyousong.practice.mytripplanner.controller;

import com.wangyousong.practice.mytripplanner.dto.LoanRequest;
import com.wangyousong.practice.mytripplanner.tool.LoanTool;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-assistant")
@Slf4j
public class LoanAssistantController {

    private final ChatClient loanChatClient;
    private final LoanTool loanTool;

    public LoanAssistantController(@Qualifier("loanChatClient") ChatClient loanChatClient, LoanTool loanTool) {
        this.loanChatClient = loanChatClient;
        this.loanTool = loanTool;
    }

    @PostMapping("/chat")
    public String chat(@Valid @RequestBody LoanRequest request) {
        log.info("User asked: {}", request.message());
        return loanChatClient
                .prompt()
                .user(request.message())
                .tools(loanTool)
                .call()
                .content();
    }


}
