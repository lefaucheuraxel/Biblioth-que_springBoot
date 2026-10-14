package com.example.demo.exception;

public class DuplicateIsbnException extends RuntimeException {

    public DuplicateIsbnException(String isbn) {
        super("Book with ISBN " + isbn + " already exists.");
    }

    public DuplicateIsbnException(String isbn, Throwable cause) {
        super("Book with ISBN " + isbn + " already exists.", cause);
    }

}
