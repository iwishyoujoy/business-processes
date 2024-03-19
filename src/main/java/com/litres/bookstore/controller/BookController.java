package com.litres.bookstore.controller;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.service.BookService;
import com.litres.bookstore.service.ReaderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.Period;


@Tag(
    name = "REST APIs for Books",
    description = "REST APIs - Create Book, Update Book, Get Book by id, Get All Books"
)
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;
    private ReaderService readerService;

    public BookController(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @Operation(
        summary = "Get All Books (by default 1st page, 10 books limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<BookDTO> books = bookService.getAllBooks(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(
        summary = "Create Book"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get Book by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @Operation(
        summary = "Add Book to Reader (considering the age restrictions)"
    )
    @PostMapping("/{bookId}/{readerId}")
    public ResponseEntity<BookDTO> addBookToReader(@PathVariable Long bookId, @PathVariable Long readerId) {
        BookDTO bookDTO = bookService.getBookById(bookId);
        ReaderDTO readerDTO = readerService.getReaderById(readerId);

        int readerAge = Period.between(readerDTO.getBirthDate(), LocalDate.now()).getYears();

        if (readerAge < bookDTO.getAgeRestriction().getAge()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(bookService.addReaderToBook(bookId, readerId), HttpStatus.CREATED);    
    }

    @Operation(
        summary = "Update Book by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);
        if (updatedBookDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedBookDTO, HttpStatus.OK);
    }    
    
    @Operation(
        summary = "Delete Book by id"
    )
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }
}
