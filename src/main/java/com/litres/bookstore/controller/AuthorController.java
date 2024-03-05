package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litres.bookstore.service.AuthorService;
import com.litres.bookstore.service.BookService;
import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;
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
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public AuthorDTO createAuthor(@RequestBody AuthorDTO author) {
        return authorService.createAuthor(author);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/login/{login}")
    public AuthorDTO getAuthorByLogin(@PathVariable String login) {
        return authorService.getAuthorByLogin(login);
    }

    @GetMapping("/{authorId}/books")
    public List<BookDTO> getBooksForAuthor(@PathVariable Long authorId) {
        return authorService.getBooksByAuthorId(authorId);
    }

    @GetMapping("/{login}/books")
    public List<BookDTO> getBooksForAuthorByLogin(@PathVariable String login) {
        return authorService.getBooksByAuthorLogin(login);
    }
}
