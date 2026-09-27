package com.wangyousong.practice.mytripplanner.sevice.impl;

import com.wangyousong.practice.mytripplanner.dto.ReviewResult;
import com.wangyousong.practice.mytripplanner.dto.TripState;
import com.wangyousong.practice.mytripplanner.sevice.Agent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Slf4j
public class ReviewAgent implements Agent<TripState, ReviewResult> {

    private final ChatClient chatClient;

    public ReviewAgent(@Qualifier("reviewChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String name() {
        return "review-agent";
    }

    @Override
    public ReviewResult execute(TripState state) {
        log.info("Agent started: {}", name());
        StopWatch sw = new StopWatch();
        sw.start();

        ReviewResult result = chatClient
                .prompt()
                .user(user -> user.text("""
                                Review the following trip plan.
                                
                                Original request:
                                {request}
                                
                                Research:
                                {research}
                                
                                Budget:
                                {budget}
                                
                                Itinerary:
                                {itinerary}
                                
                                Verify:
                                - The itinerary has the correct number of days.
                                - The total cost is within budget.
                                - The activities are practical.
                                - The itinerary is consistent with the research.
                                
                                Return:
                                - Whether the plan is valid
                                - A concise summary
                                - Any issues
                                - Recommendations
                                """)
                        .param("request", state.request())
                        .param("research", state.research())
                        .param("budget", state.budget())
                        .param("itinerary", state.itinerary()))
                .call()
                .entity(ReviewResult.class);

        sw.stop();
        log.info("Agent completed: {} , executionTimeMs={}, result={}", name(), sw.getTotalTimeMillis(), result);

        return result;
    }
}
