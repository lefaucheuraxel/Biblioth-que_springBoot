package com.example.demo.controllers;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Books;
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
    public List<Books> getAllBooks() {
         return bookService.getAllBooks();
    }

     @GetMapping("/{isbn}")
     @ResponseStatus(HttpStatus.OK)
     public Books getBookByIsbn(@PathVariable String isbn) {
         return bookService.getBookByIsbn(isbn);
     }

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public Books createBook(@Valid @RequestBody BookDto.PostInput input) throws  BadRequestException {
         Books book = new Books();

         book.setIsbn(input.getIsbn());
         book.setTitle(input.getTitle());
         book.setAuthor(input.getAuthor());
         book.setPublicationYear(input.getPublicationYear());
         book.setNumberOfPages(input.getNumberOfPages());
        return bookService.saveBook(book);
     }

     @PutMapping("/{id}")
     @ResponseStatus(HttpStatus.OK)
     public Books updateBook(@Valid @RequestBody BookDto.UpdateInput input, @PathVariable Long id) throws BadRequestException {
         Books book = new Books();

         book.setIsbn(input.getIsbn());
         book.setTitle(input.getTitle());
         book.setAuthor(input.getAuthor());
         book.setPublicationYear(input.getPublicationYear());
         book.setNumberOfPages(input.getNumberOfPages());

        return bookService.updateBook(book, id);
    }
}
