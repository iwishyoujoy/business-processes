package com.litres.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litres.bookstore.repository.ReaderRepository;
import com.litres.bookstore.model.*;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader createReader(Reader user) {
        return readerRepository.save(user);
    }

    public Reader getReaderById(Long id) {
        return readerRepository.getReferenceById(id);
    }

    public Reader getReaderByLogin(String login) {
        return readerRepository.findByLogin(login);
    }

    public List<Book> getBooksForReaderByLogin(String userLogin) {
        Reader reader = readerRepository.findByLogin(userLogin);
        if (reader != null) {
            return reader.getBooks();
        }
        return null;
    }

    public List<Book> getBooksForReaderById(Long userId) {
        Reader reader = readerRepository.getReferenceById(userId);
        if (reader != null) {
            return reader.getBooks();
        }
        return null;
    }

    public void addBookToReader(String readerLogin, Long bookId) {
        Reader reader = readerRepository.findByLogin(readerLogin);
        if (reader != null) {
            Book book = new Book();
            book.setId(bookId);
            reader.getBooks().add(book);
            readerRepository.save(reader);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}