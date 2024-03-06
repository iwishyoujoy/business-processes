package com.litres.bookstore.service.impl;

import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.exception.ResourceNotFoundException;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.repository.ReaderRepository;
import com.litres.bookstore.service.ReaderService;
import com.litres.bookstore.mapper.AutoReaderMapper;
import com.litres.bookstore.mapper.BookMapper;

import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;

@Service
@AllArgsConstructor
public class ReaderServiceImpl implements ReaderService{

    private ReaderRepository readerRepository;
    private BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<ReaderDTO> getAllReaders(){
        List<Reader> readers = readerRepository.findAll();
        return readers.stream().map((reader) -> AutoReaderMapper.MAPPER.mapToReaderDTO(reader))
            .collect(Collectors.toList());
    }

    @Override 
    public ReaderDTO createReader(ReaderDTO readerDTO){
        Reader reader = AutoReaderMapper.MAPPER.mapToReader(readerDTO);
        Reader savedReader = readerRepository.save(reader);
        ReaderDTO savedReaderDTO = AutoReaderMapper.MAPPER.mapToReaderDTO(savedReader);
        return savedReaderDTO;
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
    public List<BookDTO> getBooksForReaderByLogin(String readerLogin) {
        Reader reader = readerRepository.findByLogin(readerLogin)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "login", readerLogin));
        return reader.getBooks().stream()
            .map(book -> bookMapper.mapToBookDTO(book))
            .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksForReaderById(Long readerId) {
        Reader reader = readerRepository.findById(readerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", String.valueOf(readerId)));
        return reader.getBooks().stream()
            .map(book -> bookMapper.mapToBookDTO(book))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteReaderById(Long id){
        readerRepository.deleteById(id);
    }
}
