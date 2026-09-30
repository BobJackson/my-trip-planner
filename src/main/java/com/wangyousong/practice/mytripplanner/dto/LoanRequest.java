package com.wangyousong.practice.mytripplanner.dto;

import jakarta.validation.constraints.NotBlank;

public record LoanRequest(@NotBlank(message = "message must not be blank") String message) {
}
