package com.wangyousong.practice.mytripplanner.dto;

import java.util.List;

public record ReviewResult(
        boolean valid,
        String summary,
        List<String> issues,
        List<String> recommendations
) {
}
