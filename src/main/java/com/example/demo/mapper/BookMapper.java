package com.example.demo.mapper;

import com.example.demo.dto.book.BookCreateRequest;
import com.example.demo.dto.book.BookResponse;
import com.example.demo.dto.book.BookUpdateRequest;
import com.example.demo.entity.BookEntity;

import java.util.Optional;

public final class BookMapper {

    private BookMapper() {
    }

    public static BookEntity toEntity(BookCreateRequest request) {
        return BookEntity.builder()
                .isbn(request.isbn())
                .title(request.title())
                .author(request.author())
                .publicationYear(Optional.ofNullable(request.publicationYear()).orElse(0))
                .numberOfPages(request.numberOfPages())
                .build();
    }

    public static BookResponse toResponse(BookEntity book) {
        return new BookResponse(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getNumberOfPages());
    }

    public static void applyUpdate(BookEntity target, BookUpdateRequest request) {
        Optional.ofNullable(request.isbn()).ifPresent(target::setIsbn);
        Optional.ofNullable(request.title()).ifPresent(target::setTitle);
        Optional.ofNullable(request.author()).ifPresent(target::setAuthor);
        Optional.ofNullable(request.publicationYear()).ifPresent(target::setPublicationYear);
        Optional.ofNullable(request.numberOfPages()).ifPresent(target::setNumberOfPages);
    }

}
