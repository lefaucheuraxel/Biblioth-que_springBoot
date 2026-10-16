package com.example.demo.mapper;

import com.example.demo.dto.loan.LoanCreateRequest;
import com.example.demo.dto.loan.LoanResponse;
import com.example.demo.dto.loan.LoanUpdateRequest;
import com.example.demo.entity.BookEntity;
import com.example.demo.entity.LoanEntity;
import com.example.demo.entity.UserEntity;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static LoanEntity toEntity(LoanCreateRequest request, BookEntity book, UserEntity user) {
        return LoanEntity.builder()
                .book(book)
                .user(user)
                .borrowDate(request.borrowDate())
                .returnDate(request.returnDate())
                .build();
    }

    public static LoanResponse toResponse(LoanEntity loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBook(),
                loan.getUser(),
                loan.getBorrowDate(),
                loan.getReturnDate()
        );
    }

    public static LoanEntity applyUpdate(LoanEntity loan, LoanUpdateRequest request) {

        if (request.newReturnDate() != null) {
            loan.setReturnDate(request.newReturnDate());
        }
        return loan;
    }

}
