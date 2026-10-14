package com.example.demo.service;

import com.example.demo.dto.loan.BookAvailabilityResponse;
import com.example.demo.dto.loan.LoanCreateRequest;
import com.example.demo.dto.loan.LoanResponse;
import com.example.demo.entity.BookEntity;
import com.example.demo.entity.LoanEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.LoanConflictException;
import com.example.demo.exception.LoanNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.LoanMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final int maxActiveLoans;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository,
                       @Value("${library.loan.max-active-loans:5}") int maxActiveLoans) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.maxActiveLoans = maxActiveLoans;
    }

    /**
     * The user and book rows are locked so that concurrent requests cannot both pass the
     * limit or availability checks.
     */
    @Transactional
    public LoanResponse borrowBook(LoanCreateRequest request) {
        log.info("User with ID {} borrowing book with ISBN {}", request.userId(), request.isbn());
        UserEntity user = userRepository.findWithLockById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + request.userId() + " not found."));
        BookEntity book = bookRepository.findWithLockByIsbn(request.isbn())
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN " + request.isbn() + " not found."));

        if (loanRepository.existsByBookIdAndReturnedAtIsNull(book.getId())) {
            throw new LoanConflictException("Book with ISBN " + request.isbn() + " is already borrowed.");
        }
        if (loanRepository.countByUserIdAndReturnedAtIsNull(user.getId()) >= maxActiveLoans) {
            throw new LoanConflictException("User with ID " + user.getId() + " has reached the limit of "
                    + maxActiveLoans + " active loans.");
        }

        LoanEntity loan = LoanEntity.builder()
                .user(user)
                .book(book)
                .borrowedAt(LocalDateTime.now())
                .build();
        return LoanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional
    public LoanResponse returnBook(Long loanId) {
        log.info("Returning loan with ID {}", loanId);
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan with ID " + loanId + " not found."));
        if (loan.getReturnedAt() != null) {
            throw new LoanConflictException("Loan with ID " + loanId + " has already been returned.");
        }
        loan.setReturnedAt(LocalDateTime.now());
        return LoanMapper.toResponse(loan);
    }

    public List<LoanResponse> getLoansByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return loanRepository.findByUserIdOrderByBorrowedAtDesc(userId).stream()
                .map(LoanMapper::toResponse)
                .toList();
    }

    public List<LoanResponse> getLoansByBook(String isbn) {
        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        return loanRepository.findByBookIsbnOrderByBorrowedAtDesc(isbn).stream()
                .map(LoanMapper::toResponse)
                .toList();
    }

    public BookAvailabilityResponse getAvailability(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found."));
        boolean available = !loanRepository.existsByBookIdAndReturnedAtIsNull(book.getId());
        return new BookAvailabilityResponse(isbn, available);
    }

    /**
     * Removes the history of a book before it is deleted. Refused while the book is on loan.
     */
    @Transactional
    public void deleteLoansOfBook(Long bookId) {
        if (loanRepository.existsByBookIdAndReturnedAtIsNull(bookId)) {
            throw new LoanConflictException("Book with ID " + bookId + " is currently on loan and cannot be deleted.");
        }
        loanRepository.deleteAll(loanRepository.findByBookId(bookId));
        loanRepository.flush();
    }

    /**
     * Removes the loan history of a user before it is deleted. Refused while the user has an active loan.
     */
    @Transactional
    public void deleteLoansOfUser(Long userId) {
        if (loanRepository.existsByUserIdAndReturnedAtIsNull(userId)) {
            throw new LoanConflictException("User with ID " + userId + " has active loans and cannot be deleted.");
        }
        loanRepository.deleteAll(loanRepository.findByUserId(userId));
        loanRepository.flush();
    }

}
