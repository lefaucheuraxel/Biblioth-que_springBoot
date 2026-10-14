package com.example.demo.dto.loan;

import java.time.LocalDateTime;

public record LoanResponse(
        Long id,
        String isbn,
        String bookTitle,
        Long userId,
        String username,
        LocalDateTime borrowedAt,
        LocalDateTime returnedAt
) {
}
