package com.litres.bookstore.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.model.enums.AgeRestriction;
import com.litres.bookstore.repository.AuthorRepository;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.repository.ReaderRepository;
import com.litres.bookstore.service.BookService;
import com.litres.bookstore.mapper.AuthorMapper;
import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.mapper.ReaderMapper;
import com.litres.bookstore.exception.AgeRestrictionException;
import com.litres.bookstore.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private ReaderRepository readerRepository;
    private final BookMapper bookMapper;
    private final ReaderMapper readerMapper;
    private final AuthorMapper authorMapper;
    private final AuthorServiceImpl authorService;
    private final ReaderServiceImpl readerService;

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(book -> bookMapper.mapToBookDTO(book));
    }

    @Transactional
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorMapper.mapToAuthor(authorService.getAuthorByLogin(userDetails.getUsername()));

        Float bookCreationCost = 100.0f;
        
        if (author.getMoney() < bookCreationCost) {
            throw new IllegalArgumentException("Not enough money on the author's account");
        }

        author.setMoney(author.getMoney() - bookCreationCost);
        authorRepository.save(author);
        
        bookDTO.setAuthorId(author.getId());
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

    @Transactional
    @Override
    public BookDTO addReaderToBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book", "id", String.valueOf(bookId)));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerMapper.mapToReader(readerService.getReaderByLogin(userDetails.getUsername()));

        int readerAge = Period.between(reader.getBirthDate(), LocalDate.now()).getYears();

        if (readerAge < book.getAgeRestriction().getAge()) {
            throw new AgeRestrictionException("You are too young to read this book");
        }

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
        Book book = bookRepository.getById(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long authorId = authorService.getAuthorByLogin(userDetails.getUsername()).getId();

        if (!book.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("You do not have permission to delete this book");
        }

        bookRepository.deleteById(id);
    }

    @Override
    public Optional<BookDTO> updateBook(Long id, Map<String, Object> updates) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long authorId = authorService.getAuthorByLogin(userDetails.getUsername()).getId();
    
            if (!book.getAuthor().getId().equals(authorId)) {
                throw new IllegalArgumentException("You do not have permission to update this book");
            }

            if (updates.containsKey("title")) {
                book.setTitle((String) updates.get("title"));
            }

            if (updates.containsKey("description")) {
                book.setDescription((String) updates.get("description"));
            }

            if (updates.containsKey("content")) {
                book.setContent((String) updates.get("content"));
            }

            if (updates.containsKey("price")) {
                Object priceObject = updates.get("price");
                if (priceObject instanceof Integer) {
                    book.setPrice(Float.valueOf((Integer) priceObject));
                } else if (priceObject instanceof Float) {
                    book.setPrice((Float) priceObject);
                } else {
                    throw new IllegalArgumentException("Price must be a number");
                }
            }

            if (updates.containsKey("ageRestriction")) {
                String ageRestrictionString = (String) updates.get("ageRestriction");
                try {
                    AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRestrictionString);
                    book.setAgeRestriction(ageRestriction);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid age restriction value: " + ageRestrictionString);
                }
            }

            Book updatedBook = bookRepository.save(book);
            return Optional.of(bookMapper.mapToBookDTO(updatedBook));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean isBookExist(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return true;
        }
        return false;
    }
}