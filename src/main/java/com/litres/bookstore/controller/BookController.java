package com.litres.bookstore.controller;

import com.litres.bookstore.model.Book;
import com.litres.bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: хендлить разные ошибки - написать кастомные

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    public Book getAuthorById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

}
