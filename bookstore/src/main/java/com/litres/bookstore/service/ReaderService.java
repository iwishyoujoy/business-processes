package com.litres.bookstore.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;

@Service
public interface ReaderService {

    Page<ReaderDTO> getAllReaders(Pageable pageable);

    ReaderDTO createReader(ReaderDTO reader);

    ReaderDTO getLoggedReader();

    ReaderDTO getReaderById(Long id);

    ReaderDTO getReaderByLogin(String login);

    Page<BookDTO> getBooks(Pageable pageable);

    Page<BookDTO> getBooksById(Long readerId, Pageable pageable);

    void addBookToReader(String readerLogin, Long bookId);

    Optional<ReaderDTO> updateReader(Map<String, Object> updates);

    void deleteReader();
}