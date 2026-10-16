package com.example.demo.dto.loan;

import com.example.demo.entity.BookEntity;
import com.example.demo.entity.UserEntity;

import java.time.LocalDateTime;

public record LoanResponse (

        Long id,
        BookEntity book,
        UserEntity user,
        LocalDateTime borrowDate,
        LocalDateTime returnDate
) {}
