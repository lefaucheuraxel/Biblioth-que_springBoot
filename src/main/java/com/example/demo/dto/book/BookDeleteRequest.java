package com.example.demo.dto.book;

import jakarta.validation.constraints.NotBlank;

public record BookDeleteRequest(
        @NotBlank String isbn
) {
}
