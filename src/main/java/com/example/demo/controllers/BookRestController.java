package com.example.demo.controllers;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Books;
import com.example.demo.services.BookService;
import org.apache.coyote.BadRequestException;
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
    public List<Books> getAllBooks() {
         return bookService.getAllBooks();
    }

     @GetMapping("/{isbn}")
     public Books getBookByIsbn(@PathVariable String isbn) {
         return bookService.getBookByIsbn(isbn);
     }

     @PostMapping
     public Books createBook(@RequestBody BookDto.PostInput input) throws  BadRequestException {
        return bookService.saveBook(input);
     }

     @PutMapping("/{id}")
     public Books updateBook(@RequestBody BookDto.UpdateInput input, @PathVariable Long id) throws BadRequestException {
        return bookService.updateBook(input, id);
    }
}
