package com.example.demo.RestControllers;

import com.example.demo.Entity.Books;
import com.example.demo.Services.BookService;
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
     public Books createBook(@RequestBody Books book) {
        return bookService.saveBook(book);
     }

     @PutMapping("/{id}")
     public Books updateBook(@RequestBody Books book, @PathVariable Long id) {
        return bookService.updateBook(book, id);
    }
}
