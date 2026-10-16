package com.example.demo.dto.loan;

import jakarta.validation.constraints.NotNull;

public record LoanDeleteRequest(
        @NotNull
        Long id) {
}
