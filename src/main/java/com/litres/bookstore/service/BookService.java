package com.litres.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.BookDTO;

@Service
public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO createBook(BookDTO book);

    BookDTO getBookById(Long id);

    BookDTO addReaderToBook(Long bookId, Long readerId);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void deleteBookById(Long id);
}
