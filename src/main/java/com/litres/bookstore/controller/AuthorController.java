package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litres.bookstore.service.AuthorService;
import com.litres.bookstore.service.BookService;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private AuthorService authorService;
    private BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/login/{login}")
    public Author getAuthorByLogin(@PathVariable String login) {
        return authorService.getAuthorByLogin(login);
    }

    @GetMapping("/{authorId}/books")
    public List<Book> getBooksForAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @GetMapping("/{login}/books")
    public List<Book> getBooksForAuthorByLogin(@PathVariable String login) {
        return bookService.getBooksByAuthorLogin(login);
    }
}
