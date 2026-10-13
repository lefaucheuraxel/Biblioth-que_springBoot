package com.example.demo.dto;

import com.example.demo.model.Books;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class BookDto {


    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {

        private String isbn;
        private String title;
        private String author;
        private int publicationYear;
        private Long numberOfPages;

    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostOutput {
        private String isbn;
        private String title;
        private String author;
        private int publicationYear;
        private Long numberOfPages;

    }

    @Data
    @AllArgsConstructor
    public static class UpdateInput {
        private String isbn;
        private String title;
        private String author;
        private int publicationYear;
        private Long numberOfPages;

    }


}
