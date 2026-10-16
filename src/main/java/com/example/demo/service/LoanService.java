package com.example.demo.service;

import com.example.demo.dto.loan.LoanCreateRequest;
import com.example.demo.dto.loan.LoanDeleteRequest;
import com.example.demo.dto.loan.LoanResponse;
import com.example.demo.dto.loan.LoanUpdateRequest;
import com.example.demo.entity.BookEntity;
import com.example.demo.entity.LoanEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.LoanMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<LoanResponse> findAll() {
        return loanRepository.findAll().stream()
                .map(LoanMapper::toResponse)
                .toList();
    }

    public LoanResponse getLoanResponseById(Long id) {
        LoanEntity loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan with id " + id + " not found."));
        return LoanMapper.toResponse(loan);
    }

    @Transactional
    public LoanResponse createLoan(LoanCreateRequest request) {
        BookEntity book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new RuntimeException("Book with id " + request.bookId() + " not found."));
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User with id " + request.userId() + " not found."));
        return LoanMapper.toResponse(loanRepository.save(LoanMapper.toEntity(request, book, user)));
    }

    @Transactional
    public LoanResponse deleteLoan(LoanDeleteRequest loanDeleteRequest) {

        LoanEntity returnedLoan = loanRepository.findById(loanDeleteRequest.id()).orElseThrow(() -> new RuntimeException("Loan with id " + loanDeleteRequest.id() + " not found."));

        loanRepository.deleteById(loanDeleteRequest.id());
        return LoanMapper.toResponse(returnedLoan);
    }

    @Transactional
    public LoanResponse updateLoan(LoanUpdateRequest loanUpdateRequest, Long id) {
        LoanEntity loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan with id " + id + " not found."));
        return LoanMapper.toResponse(loanRepository.save(LoanMapper.applyUpdate(loan, loanUpdateRequest)));
    }


}
