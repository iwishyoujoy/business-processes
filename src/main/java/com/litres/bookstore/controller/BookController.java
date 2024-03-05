package com.litres.bookstore.controller;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @GetMapping("/{id}")
    public BookDTO getAuthorById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

}
