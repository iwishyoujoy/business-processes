package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litres.bookstore.service.*;
import com.litres.bookstore.model.*;

@RestController
@RequestMapping("/api/users")
public class ReaderController {

    private ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @PostMapping
    public Reader createReader(@RequestBody Reader reader) {
        return readerService.createReader(reader);
    }

    @GetMapping("/{id}")
    public Reader getReaderById(@PathVariable Long id) {
        return readerService.getReaderById(id);
    }

    @GetMapping("/login/{login}")
    public Reader getReaderByLogin(@PathVariable String login) {
        return readerService.getReaderByLogin(login);
    }

    @GetMapping("/{readerId}/books")
    public List<Book> getBooksForReader(@PathVariable Long readerId) {
        return readerService.getBooksForReaderById(readerId);
    }

    @GetMapping("/{login}/books")
    public List<Book> getBooksForReaderByLogin(@PathVariable String login) {
        return readerService.getBooksForReaderByLogin(login);
    }
}
