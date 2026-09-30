package com.wangyousong.practice.mytripplanner.controller;

import cn.hutool.core.util.StrUtil;
import com.wangyousong.practice.mytripplanner.dto.LoanChatResponse;
import com.wangyousong.practice.mytripplanner.dto.LoanRequest;
import com.wangyousong.practice.mytripplanner.tool.LoanTool;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public LoanChatResponse chat(@Valid @RequestBody LoanRequest request,
                                @RequestHeader(value = "Conversation-Id", required = false) String conversationId) {
        String convId = StrUtil.isBlank(conversationId)
                ? UUID.randomUUID().toString()
                : conversationId;
        log.info("User asked (conversationId={}): {}", convId, request.message());
        String reply = loanChatClient
                .prompt()
                .user(request.message())
                .tools(loanTool)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, convId))
                .call()
                .content();
        return new LoanChatResponse(convId, reply);
    }


}
