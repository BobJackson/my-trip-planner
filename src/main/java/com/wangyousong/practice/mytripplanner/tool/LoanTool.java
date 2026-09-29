package com.wangyousong.practice.mytripplanner.tool;

import com.wangyousong.practice.mytripplanner.dto.LoanStatusResponse;
import com.wangyousong.practice.mytripplanner.sevice.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoanTool {
    private final LoanService loanService;

    @Tool(description = "Get current loan application status using loan application id")
    public LoanStatusResponse getLoanStatus(@ToolParam(description = "Loan application id") String applicationId) {
        log.info("Tool selected by model");
        log.info(" applicationId={}", applicationId);

        return loanService.getLoanStatus(applicationId);
    }
}
