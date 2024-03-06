package com.litres.bookstore.service.impl;

import org.springframework.stereotype.Service;
import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.repository.AuthorRepository;
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
    private AuthorRepository authorRepository;
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
        Long authorId = bookDTO.getAuthorId();
        Author author = authorRepository.findById(authorId)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(authorId)));
        
        Float bookCreationCost = 100.0f;
        if (author.getMoney() < bookCreationCost) {
            throw new IllegalArgumentException("Not enough money on the author's account");
        }

        author.setMoney(author.getMoney() - bookCreationCost);
        authorRepository.save(author);
        Book book = bookMapper.mapToBook(bookDTO);
        book.setAuthor(author);
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
    public BookDTO addReaderToBook(Long bookId, Long readerId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book", "id", String.valueOf(bookId)));
        Reader reader = readerRepository.findById(readerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", String.valueOf(readerId)));
        
        Float bookCost = book.getPrice();
        if (reader.getMoney() < bookCost) {
            throw new IllegalArgumentException("Not enough money on the reader's account");
        }
        reader.setMoney(reader.getMoney() - bookCost);
        Reader savedReader = readerRepository.save(reader);
        book.getReaders().add(savedReader);
        savedReader.getBooks().add(book);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }
}