package com.litres.bookstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;

@Service
public interface ReaderService {

    Page<ReaderDTO> getAllReaders(Pageable pageable);

    ReaderDTO createReader(ReaderDTO reader);

    ReaderDTO getReaderById(Long id);

    ReaderDTO getReaderByLogin(String login);

    Page<BookDTO> getBooksForReaderByLogin(String readerLogin, Pageable pageable);

    Page<BookDTO> getBooksForReaderById(Long readerId, Pageable pageable);

    void addBookToReader(String readerLogin, Long bookId);

    ReaderDTO updateReader(Long id, ReaderDTO readerDTO);

    void deleteReaderById(Long id);
}