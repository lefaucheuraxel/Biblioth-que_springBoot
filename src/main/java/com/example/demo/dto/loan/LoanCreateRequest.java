package com.example.demo.dto.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LoanCreateRequest(
        @NotNull
        Long bookId,
        @NotNull
        Long userId,
        @NotNull
        LocalDateTime borrowDate,
        LocalDateTime returnDate) {
}
