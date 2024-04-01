package com.litres.bookstore.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.litres.bookstore.service.AuthorService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;

@Tag(
    name = "REST APIs for Authors",
    description = "REST APIs - Create Author, Update Author, Get Author by id / by login, Get All Authors, Get Books for Author by id / by login"
)
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(
        summary = "Get All Authors (by default 1st page, 10 authors limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> getAllAuthors(
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<AuthorDTO> authors = authorService.getAllAuthors(pageable);
        return new ResponseEntity<>(authors, HttpStatus.OK);
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
        summary = "Get Books for current logged Author (by default 1st page, 10 books limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/books")
    public ResponseEntity<Page<BookDTO>> getBooksForAuthor(
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<BookDTO> books = authorService.getBooks(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(
        summary = "Get Books for Author by ID (by default 1st page, 10 books limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/books/{id}")
    public ResponseEntity<Page<BookDTO>> getBooksForAuthorById(
        @PathVariable Long id,
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<BookDTO> books = authorService.getBooksById(id, pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(
        summary = "Update Author"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @PutMapping
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO updatedAuthorDTO = authorService.updateAuthor(authorDTO);
        if (updatedAuthorDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAuthorDTO, HttpStatus.OK);
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
