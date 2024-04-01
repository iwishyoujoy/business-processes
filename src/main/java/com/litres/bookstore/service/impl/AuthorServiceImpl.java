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
import com.litres.bookstore.mapper.AutoAuthorMapper;
import com.litres.bookstore.mapper.BookMapper;
import com.litres.bookstore.exception.ResourceNotFoundException;

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
        return authors.map(AutoAuthorMapper.MAPPER::mapToAuthorDTO);
    }

    @Override 
    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        Author author = AutoAuthorMapper.MAPPER.mapToAuthor(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return AutoAuthorMapper.MAPPER.mapToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO getAuthorById(Long id){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(id)));
        return AutoAuthorMapper.MAPPER.mapToAuthorDTO(author);
    }

    @Override
    public AuthorDTO getAuthorByLogin(String login){
        Author author = authorRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "login", login));
        return AutoAuthorMapper.MAPPER.mapToAuthorDTO(author);
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
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorMapper.mapToAuthor(getAuthorByLogin(userDetails.getUsername()));
        userService.updateUserData(author.getLogin(), new UserDTO(authorDTO.getLogin(), authorDTO.getPassword()));
        authorMapper.mapToUpdatedAuthor(authorDTO, author);
        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.mapToAuthorDTO(updatedAuthor);
    }
}
