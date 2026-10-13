package com.example.demo.Services;

import com.example.demo.Entity.Books;
import com.example.demo.Repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
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
        return bookRepository.findAll();
    }

    public Books getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Books saveBook(Books book) {
        return bookRepository.save(book);
    }

    public Books updateBook(Books book, Long id) {
        Optional<Books> existingBook = bookRepository.findById(id); // Check if the book exists
        if (existingBook.isEmpty()) {
            throw new RuntimeException("Book with ID " + id + " not found.");
        }
        // Update the existing book's details with the new values
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
        return bookRepository.save(bookToUpdate);
    }

}
