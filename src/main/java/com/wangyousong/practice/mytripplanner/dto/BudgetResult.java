package com.wangyousong.practice.mytripplanner.dto;

import java.math.BigDecimal;
import java.util.List;

public record BudgetResult(
        BigDecimal accommodationsCost,
        BigDecimal transportCost,
        BigDecimal foodCost,
        BigDecimal activityCost,
        BigDecimal totalCost,
        boolean withinBudget,
        List<String> recommendations
) {
}
