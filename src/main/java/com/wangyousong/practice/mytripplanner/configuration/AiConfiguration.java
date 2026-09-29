package com.wangyousong.practice.mytripplanner.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfiguration {


    @Bean
    public ChatClient loanChatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                        You are a helpful loan assistant.
                        
                        Rules:
                        1. If the user asks about loan status, use the available loan status tool.
                        2. Do not guess loan status.
                        3. If application is is missing, ask the user for the application id.
                        4. Keep the final response simple and clear.
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

}
