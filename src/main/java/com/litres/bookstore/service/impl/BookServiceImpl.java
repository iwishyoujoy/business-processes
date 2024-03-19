package com.litres.bookstore.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.repository.AuthorRepository;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.repository.ReaderRepository;
import com.litres.bookstore.service.BookService;
import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.exception.ResourceNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private ReaderRepository readerRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(book -> bookMapper.mapToBookDTO(book));
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
        
        Author author = book.getAuthor();
        Float bookCost = book.getPrice();

        if (reader.getMoney() < bookCost) {
            throw new IllegalArgumentException("Not enough money on the reader's account");
        }

        reader.setMoney(reader.getMoney() - bookCost);
        Reader savedReader = readerRepository.save(reader);

        author.setMoney(author.getMoney() + bookCost);
        authorRepository.save(author);
        
        book.getReaders().add(savedReader);
        savedReader.getBooks().add(book);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            bookMapper.mapToUpdatedBook(bookDTO, book);
            Book updatedBook = bookRepository.save(book);
            return bookMapper.mapToBookDTO(updatedBook);
        } else {
            throw new ResourceNotFoundException("Book", "id", String.valueOf(id));
        }
    }
}