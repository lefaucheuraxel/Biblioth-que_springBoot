package com.example.demo.dto.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LoanUpdateRequest(
        @NotNull
        LocalDateTime newReturnDate) {
}
