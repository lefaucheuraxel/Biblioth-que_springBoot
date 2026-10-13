package com.example.demo.controllers;

import com.example.demo.dto.BookDto;
import com.example.demo.model.BookEntity;
import com.example.demo.services.BookService;
import com.example.demo.services.BookService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto.GetOutput> getAllBooks() {
         List<BookEntity> books = bookService.getAllBooks();
         return books.stream()
                 .map(book -> BookDto.GetOutput.builder()
                         .isbn(book.getIsbn())
                         .title(book.getTitle())
                         .author(book.getAuthor())
                         .publicationYear(book.getPublicationYear())
                         .numberOfPages(book.getNumberOfPages())
                         .build())
                 .toList();
    }

     @GetMapping("/{isbn}")
     @ResponseStatus(HttpStatus.OK)
     public BookDto.GetOutput getBookByIsbn(@PathVariable String isbn) {
         BookEntity book = bookService.getBookByIsbn(isbn);
         return BookDto.GetOutput.builder()
                 .isbn(book.getIsbn())
                 .title(book.getTitle())
                 .author(book.getAuthor())
                 .publicationYear(book.getPublicationYear())
                 .numberOfPages(book.getNumberOfPages())
                 .build();
     }

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public BookDto.PostOutput createBook(@Valid @RequestBody BookDto.PostInput input) throws  BadRequestException {
         BookEntity book = new BookEntity();

         book.setIsbn(input.getIsbn());
         book.setTitle(input.getTitle());
         book.setAuthor(input.getAuthor());
         book.setPublicationYear(input.getPublicationYear());
         book.setNumberOfPages(input.getNumberOfPages());

         bookService.saveBook(book);

         return BookDto.PostOutput.builder()
                 .isbn(book.getIsbn())
                 .title(book.getTitle())
                 .author(book.getAuthor())
                 .publicationYear(book.getPublicationYear())
                 .numberOfPages(book.getNumberOfPages())
                 .build();
     }

     @PutMapping("/{id}")
     @ResponseStatus(HttpStatus.OK)
     public BookDto.UpdateOutput updateBook(@Valid @RequestBody BookDto.UpdateInput input, @PathVariable Long id) throws BadRequestException {
         BookEntity book = new BookEntity();

         book.setIsbn(input.getIsbn());
         book.setTitle(input.getTitle());
         book.setAuthor(input.getAuthor());
         book.setPublicationYear(input.getPublicationYear());
         book.setNumberOfPages(input.getNumberOfPages());

        BookEntity updatedBook = bookService.updateBook(book, id);

        return BookDto.UpdateOutput.builder()
                .isbn(updatedBook.getIsbn())
                .title(updatedBook.getTitle())
                .author(updatedBook.getAuthor())
                .publicationYear(updatedBook.getPublicationYear())
                .numberOfPages(updatedBook.getNumberOfPages())
                .build();
    }
}
