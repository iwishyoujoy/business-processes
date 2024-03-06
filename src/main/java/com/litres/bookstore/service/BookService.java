package com.litres.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.BookDTO;

@Service
public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO createBook(BookDTO book);

    BookDTO getBookById(Long id);

    void addReaderToBook(Long bookId, Long readerId);

    void deleteBookById(Long id);
}
