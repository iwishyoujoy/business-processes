package com.litres.bookstore.service.impl;

import org.springframework.stereotype.Service;
import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.repository.ReaderRepository;
import com.litres.bookstore.service.BookService;

import lombok.AllArgsConstructor;

import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.exception.ResourceNotFoundException;

import java.util.stream.Collectors;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private ReaderRepository readerRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
            .map(book -> bookMapper.mapToBookDTO(book))
            .collect(Collectors.toList());
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.mapToBook(bookDTO);
        System.out.println(book.getAuthor().getId());
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book", "id", String.valueOf(id)));
        return bookMapper.mapToBookDTO(book);
    }

    @Override
    public void addReaderToBook(Long bookId, Long readerId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book", "id", String.valueOf(bookId)));
        Reader reader = readerRepository.findById(readerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", String.valueOf(readerId)));
        
        book.getReaders().add(reader);
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }
}