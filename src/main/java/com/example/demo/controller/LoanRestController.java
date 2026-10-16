package com.example.demo.controller;

import com.example.demo.dto.loan.LoanCreateRequest;
import com.example.demo.dto.loan.LoanDeleteRequest;
import com.example.demo.dto.loan.LoanResponse;
import com.example.demo.dto.loan.LoanUpdateRequest;
import com.example.demo.entity.LoanEntity;
import com.example.demo.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanRestController {

    private final LoanService loanService;

    public LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LoanResponse> getAllLoans() {
        return loanService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanResponse getLoanById(@PathVariable Long id) {
        return loanService.getLoanResponseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse createLoan(@RequestBody @Valid LoanCreateRequest loanCreateRequest) {
        return loanService.createLoan(loanCreateRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanResponse updateLoan(@RequestBody @Valid LoanUpdateRequest request, @PathVariable Long id) {
        return loanService.updateLoan(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanResponse deleteLoan(@PathVariable Long id) {
        return loanService.deleteLoan(new LoanDeleteRequest(id));
    }

}
