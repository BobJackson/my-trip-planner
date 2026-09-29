package com.wangyousong.practice.mytripplanner.sevice.impl;

import com.wangyousong.practice.mytripplanner.dto.LoanStatusResponse;
import com.wangyousong.practice.mytripplanner.sevice.LoanService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MockedLoanService implements LoanService {

    private final Map<String, LoanStatusResponse> loanDatabase = Map.of(
            "APP123", new LoanStatusResponse(
                    "APP123",
                    "UNDER_REVIEW",
                    "Your loan application is currently under review by the credit team."
            ),
            "APP456", new LoanStatusResponse(
                    "APP456",
                    "APPROVED",
                    "Your loan application has been approved."
            )
    );


    @Override
    public LoanStatusResponse getLoanStatus(String applicationId) {
        return loanDatabase.getOrDefault(
                applicationId,
                new LoanStatusResponse(
                        applicationId,
                        "NOT_FOUND",
                        "No loan application was found for the application id."
                )
        );
    }
}
