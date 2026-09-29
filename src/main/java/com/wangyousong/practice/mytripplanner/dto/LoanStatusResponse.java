package com.wangyousong.practice.mytripplanner.dto;

public record LoanStatusResponse(
        String applicationId,
        String status,
        String message) {
}
