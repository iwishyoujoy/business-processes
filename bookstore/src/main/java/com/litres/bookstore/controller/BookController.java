package com.litres.bookstore.controller;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.messaging.EmailGateway;
import com.litres.bookstore.messaging.Letter;
import com.litres.bookstore.service.BookService;
import com.litres.bookstore.service.ReaderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;

import java.util.Map;

@Tag(
    name = "REST APIs for Books",
    description = "REST APIs - Create Book, Update Book, Get Book by id, Get All Books"
)
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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
    @PreAuthorize("hasRole('AUTHOR')")
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
    @PostMapping("/{bookId}")
    @PreAuthorize("hasRole('READER')")
    public ResponseEntity<BookDTO> addBookToReader(@PathVariable Long bookId) {
        return new ResponseEntity<>(bookService.addReaderToBook(bookId), HttpStatus.CREATED);    
    }

    @Operation(
        summary = "Update Book by id (can be partial)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return bookService.updateBook(id, updates)
            .map(bookDTO -> new ResponseEntity<>(bookDTO, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }    
    
    @Operation(
        summary = "Delete Book by id"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id){
        if (!bookService.isBookExist(id)) {
            return new ResponseEntity<String>("Couldn't find a book with this ID", HttpStatus.NOT_FOUND);
        }
        bookService.deleteBookById(id);
        return new ResponseEntity<String>("Book was deleted successfully", HttpStatus.OK);
    }
}
