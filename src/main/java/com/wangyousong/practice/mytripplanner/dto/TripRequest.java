package com.wangyousong.practice.mytripplanner.dto;

import java.math.BigDecimal;

public record TripRequest(
        String destination,
        int numbersOfDays,
        BigDecimal budget,
        String preferences
) {
}
