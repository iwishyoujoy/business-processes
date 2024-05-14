package com.litres.bookstore.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.litres.bookstore.dto.BookDTO;

@Service
public interface BookService {

    Page<BookDTO> getAllBooks(Pageable pageable);

    BookDTO createBook(BookDTO book);

    BookDTO getBookById(Long id);

    boolean isBookExist(Long id);

    BookDTO addReaderToBook(Long bookId);

    Optional<BookDTO> updateBook(Long id, Map<String, Object> updates);

    void deleteBookById(Long id);
}
