package com.wangyousong.practice.mytripplanner.sevice.impl;

import com.wangyousong.practice.mytripplanner.dto.BudgetResult;
import com.wangyousong.practice.mytripplanner.dto.TripState;
import com.wangyousong.practice.mytripplanner.sevice.Agent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BudgetAgent implements Agent<TripState, BudgetResult> {

    private final ChatClient chatClient;

    public BudgetAgent(@Qualifier("budgetChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String name() {
        return "budget-agent";
    }

    @Override
    public BudgetResult execute(TripState state) {
        return chatClient
                .prompt()
                .user(user -> user.text("""
                                User request:
                                {request}
                                
                                Research produced by the research agent:
                                {research}
                                
                                Estimate accommodation, transportation, food and activity costs.
                                
                                The total budget must not exceed:{budget}
                                """)
                        .param("request", state.request())
                        .param("research", state.research())
                        .param("budget", state.request().budget())
                )
                .call()
                .entity(BudgetResult.class);
    }
}
