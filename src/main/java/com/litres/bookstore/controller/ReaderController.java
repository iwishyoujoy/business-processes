package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.litres.bookstore.service.*;
import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;

@RestController
@RequestMapping("/api/users")
public class ReaderController {

    private ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public ResponseEntity<List<ReaderDTO>> getAllReaders() {
        return new ResponseEntity<>(readerService.getAllReaders(), HttpStatus.OK);
    }

    @PostMapping
    public ReaderDTO createReader(@RequestBody ReaderDTO readerDTO) {
        return readerService.createReader(readerDTO);
    }

    @GetMapping("/{id}")
    public ReaderDTO getReaderById(@PathVariable Long id) {
        return readerService.getReaderById(id);
    }

    @GetMapping("/login/{login}")
    public ReaderDTO getReaderByLogin(@PathVariable String login) {
        return readerService.getReaderByLogin(login);
    }

    @GetMapping("/{readerId}/books")
    public List<BookDTO> getBooksForReader(@PathVariable Long readerId) {
        return readerService.getBooksForReaderById(readerId);
    }

    @GetMapping("/{login}/books")
    public List<BookDTO> getBooksForReaderByLogin(@PathVariable String login) {
        return readerService.getBooksForReaderByLogin(login);
    }
}
