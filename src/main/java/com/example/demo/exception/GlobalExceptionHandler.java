package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ProblemDetail> catchBookNotFound(BookNotFoundException e) {
        log.warn("Book not found: {}", e.getMessage());
        return problem(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<ProblemDetail> catchDuplicateIsbn(DuplicateIsbnException e) {
        log.warn("Duplicate ISBN: {}", e.getMessage());
        return problem(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ProblemDetail> catchConcurrentModification(ObjectOptimisticLockingFailureException e) {
        log.warn("Concurrent modification detected: {}", e.getMessage());
        return problem(HttpStatus.CONFLICT, "The book was modified by another request. Please retry.");
    }

    /**
     * Keeps the intended status of Spring's own ErrorResponse exceptions (e.g. ResponseStatusException)
     * and returns a generic 500 for anything else.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> catchAny(Exception e, WebRequest request) {
        if (e instanceof ErrorResponse errorResponse) {
            return handleExceptionInternal(e, errorResponse.getBody(), errorResponse.getHeaders(),
                    errorResponse.getStatusCode(), request);
        }
        log.error("An unexpected error occurred while processing the request.", e);
        return ResponseEntity.internalServerError()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred."));
    }

    private static ResponseEntity<ProblemDetail> problem(HttpStatus status, String detail) {
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
    }

}
