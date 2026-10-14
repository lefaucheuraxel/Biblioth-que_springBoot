package com.example.demo.exception;

public class LoanConflictException extends RuntimeException {

    public LoanConflictException(String message) {
        super(message);
    }

}
