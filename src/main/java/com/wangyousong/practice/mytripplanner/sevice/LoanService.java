package com.wangyousong.practice.mytripplanner.sevice;

import com.wangyousong.practice.mytripplanner.dto.LoanStatusResponse;

public interface LoanService {
    LoanStatusResponse getLoanStatus(String applicationId);
}
