package com.example.demo.dto;

public record BookResponse(
        String isbn,
        String title,
        String author,
        int publicationYear,
        Long numberOfPages
) {
}
