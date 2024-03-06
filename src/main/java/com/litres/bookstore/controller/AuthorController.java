package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litres.bookstore.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;

@Tag(
    name = "REST APIs for Authors",
    description = "REST APIs - Create Author, Get Author by id / by login, Get All Authors, Get Books for Author by id / by login"
)
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(
        summary = "Get All Authors"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @Operation(
        summary = "Create Author"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO author) {
        return new ResponseEntity<>(authorService.createAuthor(author), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get Author by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    @Operation(
        summary = "Get Author by login"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/login/{login}")
    public ResponseEntity<AuthorDTO> getAuthorByLogin(@PathVariable String login) {
        return new ResponseEntity<>(authorService.getAuthorByLogin(login), HttpStatus.OK);
    }

    @Operation(
        summary = "Get Books for Author by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/id/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksForAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.getBooksByAuthorId(id), HttpStatus.OK);
    }

    @Operation(
        summary = "Get Books for Author by login"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/login/{login}/books")
    public ResponseEntity<List<BookDTO>> getBooksForAuthorByLogin(@PathVariable String login) {
        return new ResponseEntity<>(authorService.getBooksByAuthorLogin(login), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete Author by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/id/{id}")
    public void getBooksForAuthorByLogin(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
    }
}
