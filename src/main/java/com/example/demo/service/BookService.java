package com.example.demo.service;

import com.example.demo.dto.book.BookCreateRequest;
import com.example.demo.dto.book.BookResponse;
import com.example.demo.dto.book.BookUpdateRequest;
import com.example.demo.entity.BookEntity;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.DuplicateIsbnException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        log.info("Retrieving books: page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());
        return bookRepository.findAll(pageable).map(BookMapper::toResponse);
    }

    public BookResponse getBookByIsbn(String isbn) {
        log.info("Retrieving book with ISBN {}", isbn);
        return bookRepository.findByIsbn(isbn)
                .map(BookMapper::toResponse)
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found."));
    }

    @Transactional
    public BookResponse saveBook(BookCreateRequest request) {
        log.info("Saving book with ISBN {}", request.isbn());
        ensureIsbnAvailable(request.isbn());
        return BookMapper.toResponse(persist(BookMapper.toEntity(request)));
    }

    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        log.info("Updating book with ID {}", id);
        BookEntity existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));

        Optional.ofNullable(request.isbn())
                .filter(isbn -> !isbn.equals(existing.getIsbn()))
                .ifPresent(this::ensureIsbnAvailable);

        BookMapper.applyUpdate(existing, request);
        return BookMapper.toResponse(persist(existing));
    }

    private void ensureIsbnAvailable(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new DuplicateIsbnException(isbn);
        }
    }

    /**
     * The existsByIsbn check can race with a concurrent insert; the unique constraint on isbn
     * is the real guard, so a violation at flush time is mapped to the same 409 error.
     */
    private BookEntity persist(BookEntity book) {
        try {
            return bookRepository.saveAndFlush(book);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateIsbnException(book.getIsbn(), e);
        }
    }

}
