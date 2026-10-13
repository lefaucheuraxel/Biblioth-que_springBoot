package com.example.demo.services;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Books;
import com.example.demo.model.exceptions.BookCreationException;
import com.example.demo.persistence.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Books> getAllBooks() {
        log.info("Retrieving all books");
        return bookRepository.findAll();
    }

    public Books getBookByIsbn(@NonNull String isbn) {
        log.info("Retrieving book with ISBN {}", isbn);
        Books book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ISBN " + isbn + " not found.");
        }
        return book;
    }

    public Books saveBook(@NonNull Books book) throws BookCreationException {
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new BookCreationException("ISBN must not be blank.");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new BookCreationException("Title must not be blank.");
        }
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new BookCreationException("Author must not be blank.");
        }
        if (book.getNumberOfPages() == null || book.getNumberOfPages() <= 0) {
            throw new BookCreationException("Number of pages must be greater than zero.");
        }

        log.info("Saving book {}", book);
        bookRepository.save(book);
        return book;
    }

    public Books updateBook(@NonNull Books book, @NonNull Long id) throws BadRequestException {

        Books existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Book with ID " + id + " not found."));

        Books bookUpdated = getBookUpdate(book, existingBook);
        log.info("Updating book {}", bookUpdated);

        bookRepository.save(bookUpdated);
        return bookUpdated;
    }

    private static @NonNull Books getBookUpdate(@NonNull Books book, @NonNull Books bookToUpdate) {
        if(book.getIsbn() != null) {
            bookToUpdate.setIsbn(book.getIsbn());
        }
        if(book.getTitle() != null) {
            bookToUpdate.setTitle(book.getTitle());
        }
        if(book.getAuthor() != null) {
            bookToUpdate.setAuthor(book.getAuthor());
        }
        if(book.getPublicationYear() != 0) {
            bookToUpdate.setPublicationYear(book.getPublicationYear());
        }
        if(book.getNumberOfPages() != null) {
            bookToUpdate.setNumberOfPages(book.getNumberOfPages());
        }
        return bookToUpdate;
    }

}
