package com.litres.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;

@Service
public interface ReaderService {

    List<ReaderDTO> getAllReaders();

    ReaderDTO createReader(ReaderDTO reader);

    ReaderDTO getReaderById(Long id);

    ReaderDTO getReaderByLogin(String login);

    List<BookDTO> getBooksForReaderByLogin(String readerLogin);

    List<BookDTO> getBooksForReaderById(Long readerId);

    void addBookToReader(String readerLogin, Long bookId);

    ReaderDTO updateReader(Long id, ReaderDTO readerDTO);

    void deleteReaderById(Long id);
}