package com.litres.bookstore.service;

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

    BookDTO addReaderToBook(Long bookId, Long readerId);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void deleteBookById(Long id);
}
