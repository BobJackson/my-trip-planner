package com.wangyousong.practice.mytripplanner.dto;

import java.util.List;

public record ResearchResult(
        List<String> attractions,
        List<String> travelTips,
        List<String> foodOptions
) {
}
