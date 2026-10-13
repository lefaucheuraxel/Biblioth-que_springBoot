package com.example.demo.dto;

import com.example.demo.model.BookEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class BookDto {


    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {

        @NotNull
        @NotBlank
        private String isbn;
        @NotNull
        @NotBlank
        private String title;
        @NotNull
        @NotBlank
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
    @Builder
    public static class UpdateInput {
        private String isbn;
        private String title;
        private String author;
        private int publicationYear;
        private Long numberOfPages;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class UpdateOutput {
        private String isbn;
        private String title;
        private String author;
        private int publicationYear;
        private Long numberOfPages;
    }
    @Data
    @AllArgsConstructor
    @Builder
    public static class GetOutput {
        private String isbn;
        private String title;
        private String author;
        private int publicationYear;
        private Long numberOfPages;
    }


}
