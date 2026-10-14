package com.example.demo.dto.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanCreateRequest(
        @NotNull Long userId,
        @NotBlank String isbn
) {
}
