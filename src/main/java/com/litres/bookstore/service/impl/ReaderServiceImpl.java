package com.litres.bookstore.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.dto.UserDTO;
import com.litres.bookstore.exception.ResourceNotFoundException;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.repository.ReaderRepository;
import com.litres.bookstore.service.ReaderService;
import com.litres.bookstore.mapper.AutoReaderMapper;
import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.mapper.ReaderMapper;

import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReaderServiceImpl implements ReaderService{

    private ReaderRepository readerRepository;
    private BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ReaderMapper readerMapper;
    private final UserServiceImpl userService;

    @Override
    public Page<ReaderDTO> getAllReaders(Pageable pageable){
        Page<Reader> readers = readerRepository.findAll(pageable);
        return readers.map((reader) -> AutoReaderMapper.MAPPER.mapToReaderDTO(reader));
    }

    @Override 
    public ReaderDTO createReader(ReaderDTO readerDTO){
        Reader reader = readerMapper.mapToReader(readerDTO);
        Reader savedReader = readerRepository.save(reader);
        return readerMapper.mapToReaderDTO(savedReader);
    }

    @Override
    public ReaderDTO getReaderById(Long id){
        Reader reader = readerRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Reader", "id", String.valueOf(id))
        );
        return AutoReaderMapper.MAPPER.mapToReaderDTO(reader);
    }

    @Override
    public ReaderDTO getReaderByLogin(String login){
        Reader reader = readerRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "login", login));
        return AutoReaderMapper.MAPPER.mapToReaderDTO(reader);
    }

    @Override
    public void addBookToReader(String readerLogin, Long bookId) {
        Reader reader = readerRepository.findByLogin(readerLogin)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "login", readerLogin));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book", "id", String.valueOf(bookId)));
        
        reader.getBooks().add(book);
        readerRepository.save(reader);
    }

   @Override
    public Page<BookDTO> getBooks(Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerMapper.mapToReader(getReaderByLogin(userDetails.getUsername()));
        List<Book> books = reader.getBooks();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        if (start >= end) {
            return Page.empty();
        }
        return new PageImpl<>(books.subList(start, end).stream()
                .map(book -> bookMapper.mapToBookDTO(book))
                .collect(Collectors.toList()), pageable, books.size());
    }
    
    @Override
    public Page<BookDTO> getBooksById(Long readerId, Pageable pageable) {
        Reader reader = readerRepository.findById(readerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", String.valueOf(readerId)));
        List<Book> books = reader.getBooks();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        if (start >= end) {
            return Page.empty();
        }
        return new PageImpl<>(books.subList(start, end).stream()
                .map(book -> bookMapper.mapToBookDTO(book))
                .collect(Collectors.toList()), pageable, books.size());
    }

    @Override
    public void deleteReaderById(Long id){
        readerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ReaderDTO updateReader(ReaderDTO readerDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerMapper.mapToReader(getReaderByLogin(userDetails.getUsername()));
        userService.updateUserData(reader.getLogin(), new UserDTO(readerDTO.getLogin(), readerDTO.getPassword()));
        readerMapper.mapToUpdatedReader(readerDTO, reader);
        Reader updatedReader = readerRepository.save(reader);
        return readerMapper.mapToReaderDTO(updatedReader);
    }
}
