package com.example.demo.mapper;

import com.example.demo.dto.loan.LoanResponse;
import com.example.demo.entity.LoanEntity;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static LoanResponse toResponse(LoanEntity loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBook().getIsbn(),
                loan.getBook().getTitle(),
                loan.getUser().getId(),
                loan.getUser().getUsername(),
                loan.getBorrowedAt(),
                loan.getReturnedAt());
    }


}
