package com.litres.bookstore.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.dto.UserDTO;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.repository.AuthorRepository;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.service.AuthorService;

import lombok.AllArgsConstructor;

import com.litres.bookstore.mapper.AuthorMapper;
import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final UserServiceImpl userService;

    @Override
    public Page<AuthorDTO> getAllAuthors(Pageable pageable){
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map((author) -> authorMapper.mapToAuthorDTO(author));
    }

    @Override 
    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        Author author = authorMapper.mapToAuthor(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.mapToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO getAuthorById(Long id){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(id)));
        return authorMapper.mapToAuthorDTO(author);
    }

    @Override
    public AuthorDTO getAuthorByLogin(String login){
        Author author = authorRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "login", login));
        return authorMapper.mapToAuthorDTO(author);
    }

    @Override
    public Page<BookDTO> getBooks(Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorMapper.mapToAuthor(getAuthorByLogin(userDetails.getUsername()));
        return bookRepository.findByAuthorId(author.getId(), pageable).map(book -> bookMapper.mapToBookDTO(book));
    }

    @Override
    public Page<BookDTO> getBooksById(Long authorId, Pageable pageable) {
        Author author = authorRepository.findById(authorId)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(authorId)));
        return bookRepository.findByAuthorId(author.getId(), pageable).map(book -> bookMapper.mapToBookDTO(book));
    }

    @Transactional
    @Override
    public void deleteAuthor(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthorDTO author = getAuthorByLogin(userDetails.getUsername());
        authorRepository.deleteById(author.getId());
        userService.deleteUserByUsername(author.getLogin());
    }

    @Transactional
    @Override
    public Optional<AuthorDTO> updateAuthor(Map<String, Object> updates) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorMapper.mapToAuthor(getAuthorByLogin(userDetails.getUsername()));
        
        if (updates.containsKey("login")){
            userService.updateUserData(author.getLogin(), new UserDTO((String) updates.get("login"), author.getPassword()));
            author.setLogin((String) updates.get("login"));
        }

        if (updates.containsKey("password")){
            userService.updateUserData(author.getLogin(), new UserDTO(author.getPassword(), (String) updates.get("password")));
            author.setPassword((String) updates.get("password"));
        }

        if (updates.containsKey("name")){
            author.setName((String) updates.get("name"));
        }

        if (updates.containsKey("surname")){
            author.setSurname((String) updates.get("surname"));
        }

        if (updates.containsKey("email")){
            author.setEmail((String) updates.get("email"));
        }

        if (updates.containsKey("money")) {
            Object moneyObject = updates.get("money");
            if (moneyObject instanceof Integer) {
                author.setMoney(Float.valueOf((Integer) moneyObject));
            } else if (moneyObject instanceof Float) {
                author.setMoney((Float) moneyObject);
            } else {
                throw new IllegalArgumentException("Money must be a number");
            }
        }

        Author updatedAuthor = authorRepository.save(author);
        return Optional.of(authorMapper.mapToAuthorDTO(updatedAuthor));
    }

    @Override
    public AuthorDTO getLoggedAuthor() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = getAuthorByLogin(userDetails.getUsername()).getId();
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(id)));
        return authorMapper.mapToAuthorDTO(author);
    }
}
