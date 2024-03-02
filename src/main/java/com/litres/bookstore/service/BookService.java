package com.litres.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litres.bookstore.repository.AuthorRepository;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.getReferenceById(id);
    }

    public List<Book> getBooksByAuthorLogin(String authorLogin) {
        return bookRepository.findByAuthorLogin(authorLogin);
    }

    public List<Book> getBooksByAuthorId(Long id) {
        return bookRepository.findByAuthorId(id);
    }

    public void addBookToAuthor(Long authorId, Book book) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        book.setAuthor(author);
        bookRepository.save(book);
    }

    // Добавьте другие методы для обновления, удаления книги и т.д.
}
