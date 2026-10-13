package com.example.demo.services;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Books;
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

    public Books getBookByIsbn(String isbn) {
        log.info("Retrieving book with ISBN {}", isbn);
        Books book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ISBN " + isbn + " not found.");
        }
        return book;
    }

    public Books saveBook(BookDto.PostInput input) throws BadRequestException {
        if (input == null) {
            throw new BadRequestException("Book data must not be null.");
        }
        if (input.getIsbn() == null || input.getIsbn().isBlank()) {
            throw new BadRequestException("ISBN must not be blank.");
        }
        if (input.getTitle() == null || input.getTitle().isBlank()) {
            throw new BadRequestException("Title must not be blank.");
        }
        if (input.getAuthor() == null || input.getAuthor().isBlank()) {
            throw new BadRequestException("Author must not be blank.");
        }
        if (input.getNumberOfPages() == null || input.getNumberOfPages() <= 0) {
            throw new BadRequestException("Number of pages must be greater than zero.");
        }

        Books book = new Books();

        book.setIsbn(input.getIsbn());
        book.setTitle(input.getTitle());
        book.setAuthor(input.getAuthor());
        book.setPublicationYear(input.getPublicationYear());
        book.setNumberOfPages(input.getNumberOfPages());

        log.info("Saving book {}", book);

        return bookRepository.save(book);
    }

    public Books updateBook(BookDto.UpdateInput input, Long id) throws BadRequestException {
        if (input == null) {
            throw new BadRequestException("Book data must not be null.");
        }
        Books existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book with ID " + id + " not found."
                ));
        Books bookToUpdate = getBookToUpdate(input, existingBook);
        log.info("Updating book {}", bookToUpdate);
        return bookRepository.save(bookToUpdate);
    }

    private static @NonNull Books getBookToUpdate(BookDto.UpdateInput book, Books bookToUpdate) {
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
