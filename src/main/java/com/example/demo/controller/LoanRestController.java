package com.example.demo.controller;

import com.example.demo.dto.loan.BookAvailabilityResponse;
import com.example.demo.dto.loan.LoanCreateRequest;
import com.example.demo.dto.loan.LoanResponse;
import com.example.demo.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanRestController {

    private final LoanService loanService;

    public LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse borrowBook(@Valid @RequestBody LoanCreateRequest request) {
        return loanService.borrowBook(request);
    }

    @PutMapping("/{id}/return")
    public LoanResponse returnBook(@PathVariable Long id) {
        return loanService.returnBook(id);
    }

    /**
     * Returns the loan history either of a user ({@code userId}) or of a book ({@code isbn}), not both.
     */
    @GetMapping
    public List<LoanResponse> getLoans(@RequestParam(required = false) Long userId,
                                       @RequestParam(required = false) String isbn) {
        if ((userId == null) == (isbn == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide exactly one of 'userId' or 'isbn'.");
        }
        return userId != null ? loanService.getLoansByUser(userId) : loanService.getLoansByBook(isbn);
    }

}
