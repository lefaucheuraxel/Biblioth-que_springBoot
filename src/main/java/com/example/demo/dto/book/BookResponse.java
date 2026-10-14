package com.example.demo.dto.book;

public record BookResponse(
        String isbn,
        String title,
        String author,
        int publicationYear,
        Long numberOfPages
) {
}
