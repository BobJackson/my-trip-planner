package com.wangyousong.practice.mytripplanner.sevice.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wangyousong.practice.mytripplanner.dto.ResearchResult;
import com.wangyousong.practice.mytripplanner.dto.TripRequest;
import com.wangyousong.practice.mytripplanner.sevice.Agent;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ResearchAgent implements Agent<TripRequest, ResearchResult> {

    private final ChatClient chatClient;

    public ResearchAgent(@Qualifier("researchChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String name() {
        return "research-agent";
    }

    @Override
    public ResearchResult execute(TripRequest request) {
        return chatClient
                .prompt()
                .user(user -> user.text("""
                        Research a trip using these requirements:
                        
                        Destination: {destination}
                        Number of days: {numbersOfDays}
                        Budget: {budget}
                        Preferences: {preferences}
                        
                        Return suitable attractions, food options and imports travel tips
                        """).params(BeanUtil.beanToMap(request)))
                .call()
                .entity(ResearchResult.class);
    }
}
