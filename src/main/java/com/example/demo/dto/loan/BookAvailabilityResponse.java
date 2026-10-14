package com.example.demo.dto.loan;

public record BookAvailabilityResponse(
        String isbn,
        boolean available
) {
}
