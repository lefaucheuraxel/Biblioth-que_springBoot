package com.example.demo.controllers;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Books;
import com.example.demo.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    // Define your RESTful endpoints here, for example:
     @GetMapping
     public List<Books> getAllBooks() {
         // Logic to retrieve all books
         return bookService.getAllBooks();
     }

     @GetMapping("/{isbn}")
     public Books getBookByIsbn(@PathVariable String isbn) {
         return bookService.getBookByIsbn(isbn);
     }

     @PostMapping
     public Books createBook(@RequestBody BookDto.PostInput input) {

        return bookService.saveBook(input);
     }

     @PutMapping("/{id}")
     public Books updateBook(@RequestBody BookDto.UpdateInput input, @PathVariable Long id) {
        return bookService.updateBook(input, id);
    }
}
