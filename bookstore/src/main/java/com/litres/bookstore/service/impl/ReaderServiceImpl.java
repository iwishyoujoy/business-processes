package com.litres.bookstore.service.impl;

import org.springframework.data.domain.Page;
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
import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.mapper.ReaderMapper;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
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
        return readers.map((reader) -> readerMapper.mapToReaderDTO(reader));
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
        return readerMapper.mapToReaderDTO(reader);
    }

    @Override
    public ReaderDTO getReaderByLogin(String login){
        Reader reader = readerRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "login", login));
        return readerMapper.mapToReaderDTO(reader);
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
        return bookRepository.findByReaderId(reader.getId(), pageable).map(book -> bookMapper.mapToBookDTO(book));
    }
    
    @Override
    public Page<BookDTO> getBooksById(Long readerId, Pageable pageable) {
        Reader reader = readerRepository.findById(readerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", String.valueOf(readerId)));
        return bookRepository.findByReaderId(reader.getId(), pageable).map(book -> bookMapper.mapToBookDTO(book));
    }

    @Override
    public void deleteReader(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerMapper.mapToReader(getReaderByLogin(userDetails.getUsername()));
        readerRepository.deleteById(reader.getId());
        userService.deleteUserByUsername(reader.getLogin());
    }

    @Transactional
    @Override
    public Optional<ReaderDTO> updateReader(Map<String, Object> updates) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reader reader = readerMapper.mapToReader(getReaderByLogin(userDetails.getUsername()));
        
        if (updates.containsKey("login")){
            userService.updateUserData(reader.getLogin(), new UserDTO((String) updates.get("login"), reader.getEmail(), reader.getPassword()));
            reader.setLogin((String) updates.get("login"));
        }

        if (updates.containsKey("password")){
            userService.updateUserData(reader.getLogin(), new UserDTO(reader.getLogin(), reader.getEmail(), (String) updates.get("password")));
            reader.setPassword((String) updates.get("password"));
        }

        if (updates.containsKey("name")){
            reader.setName((String) updates.get("name"));
        }

        if (updates.containsKey("surname")){
            reader.setSurname((String) updates.get("surname"));
        }

        if (updates.containsKey("email")){
            userService.updateUserData(reader.getLogin(), new UserDTO(reader.getLogin(), (String) updates.get("email"), reader.getPassword()));
            reader.setEmail((String) updates.get("email"));
        }

        // if (updates.containsKey("money")) {
        //     Object moneyObject = updates.get("money");
        //     if (moneyObject instanceof Integer) {
        //         reader.setMoney(Float.valueOf((Integer) moneyObject));
        //     } else if (moneyObject instanceof Float) {
        //         reader.setMoney((Float) moneyObject);
        //     } else {
        //         throw new IllegalArgumentException("Money must be a number");
        //     }
        // }

        if (updates.containsKey("birthDate")) {
            Object birthObject = updates.get("birthDate");
            try {
                reader.setBirthDate((LocalDate) birthObject);
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Birth date should be in format YYYY-MM-DD");
            }
        }

        Reader updatedReader = readerRepository.save(reader);
        return Optional.of(readerMapper.mapToReaderDTO(updatedReader));
    }

    @Override
    public ReaderDTO getLoggedReader() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = getReaderByLogin(userDetails.getUsername()).getId();
        Reader reader = readerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", String.valueOf(id)));
        return readerMapper.mapToReaderDTO(reader);
    }
}
