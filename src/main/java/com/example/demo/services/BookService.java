package com.example.demo.services;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Books;
import com.example.demo.persistence.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Books> getAllBooks() {
        // Logic to retrieve all books from the database or any other source
        // For demonstration purposes, returning an empty list
        log.info("Getting all books");
        return bookRepository.findAll();
    }

    public Books getBookByIsbn(String isbn) {
        log.info("Getting book by isbn {}", isbn);
        return bookRepository.findByIsbn(isbn);
    }

    public Books saveBook(BookDto.PostInput input) {

        Books book = new Books();
        book.setIsbn(input.getIsbn());
        book.setTitle(input.getTitle());
        book.setAuthor(input.getAuthor());
        book.setPublicationYear(input.getPublicationYear());
        book.setNumberOfPages(input.getNumberOfPages());

        log.info("Saving book {}", book);

        return bookRepository.save(book);
    }

    public Books updateBook(BookDto.UpdateInput input, Long id) {
        Optional<Books> existingBook = bookRepository.findById(id); // Check if the book exists
        if (existingBook.isEmpty()) {
            throw new RuntimeException("Book with ID " + id + " not found.");
        }
        // Update the existing book's details with the new values
        Books bookToUpdate = getBookToUpdate(input, existingBook);
        log.info("Updating book {}", bookToUpdate);
        return bookRepository.save(bookToUpdate);
    }

    private static @NonNull Books getBookToUpdate(BookDto.UpdateInput book, Optional<Books> existingBook) {
        Books bookToUpdate = existingBook.get();

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
