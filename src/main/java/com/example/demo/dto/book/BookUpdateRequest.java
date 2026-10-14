package com.example.demo.dto.book;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

/**
 * Partial update: every field is optional, and a null field leaves the stored value unchanged.
 * The pattern rejects blank strings while still accepting null.
 */
public record BookUpdateRequest(
        @Pattern(regexp = NOT_BLANK_IF_PRESENT) String isbn,
        @Pattern(regexp = NOT_BLANK_IF_PRESENT) String title,
        @Pattern(regexp = NOT_BLANK_IF_PRESENT) String author,
        Integer publicationYear,
        @Positive Long numberOfPages
) {
    static final String NOT_BLANK_IF_PRESENT = "(?!\\s*$).+";
}
