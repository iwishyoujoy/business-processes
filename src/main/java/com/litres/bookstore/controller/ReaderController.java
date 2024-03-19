package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.litres.bookstore.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;

@Tag(
    name = "REST APIs for Readers",
    description = "REST APIs - Create Reader, Update Reader, Get Reader by id / by login, Get All Readers, Get Books for User by id / by login"
)
@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    private ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Operation(
        summary = "Get All Readers (by default 1st page, 10 readers limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<Page<ReaderDTO>> getAllReaders(
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<ReaderDTO> readers = readerService.getAllReaders(pageable);
        return new ResponseEntity<>(readers, HttpStatus.OK);
    }

    @Operation(
        summary = "Create Reader"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<ReaderDTO> createReader(@RequestBody ReaderDTO readerDTO) {
        return new ResponseEntity<>(readerService.createReader(readerDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get Reader by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable Long id) {
        return new ResponseEntity<>(readerService.getReaderById(id), HttpStatus.OK);
    }

    @Operation(
        summary = "Get Reader by login"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/login/{login}")
    public ResponseEntity<ReaderDTO> getReaderByLogin(@PathVariable String login) {
        return new ResponseEntity<>(readerService.getReaderByLogin(login), HttpStatus.OK);
    }

    @Operation(
        summary = "Get Books for reader by id (by default 1st page, 10 books limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/id/{id}/books")
    public ResponseEntity<Page<BookDTO>> getBooksForReader(
        @PathVariable Long id,
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<BookDTO> books = readerService.getBooksForReaderById(id, pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(
        summary = "Get Books for reader by login (by default 1st page, 10 books limit)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/login/{login}/books")
    public ResponseEntity<Page<BookDTO>> getBooksForReaderByLogin(
        @PathVariable String login,
        @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Sort by") @RequestParam(defaultValue = "id") String sortBy,
        @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<BookDTO> books = readerService.getBooksForReaderByLogin(login, pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(
        summary = "Update Reader by id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ReaderDTO> updateReaderById(@PathVariable Long id, @RequestBody ReaderDTO readerDTO) {
        ReaderDTO updatedReaderDTO = readerService.updateReader(id, readerDTO);
        if (updatedReaderDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedReaderDTO, HttpStatus.OK);
    }

    @Operation(
        summary = "Delete Reader by id"
    )
    @DeleteMapping("/id/{id}")
    public void deleteReaderById(@PathVariable Long id){
        readerService.deleteReaderById(id);
    }
}
