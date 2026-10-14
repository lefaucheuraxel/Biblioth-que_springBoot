package com.example.demo.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookCreateRequest(
        @NotBlank String isbn,
        @NotBlank String title,
        @NotBlank String author,
        @NotNull Integer publicationYear,
        @NotNull @Positive Long numberOfPages
) {
}
