package com.litres.bookstore.service.impl;

import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.repository.AuthorRepository;
import com.litres.bookstore.repository.BookRepository;
import com.litres.bookstore.service.AuthorService;
import com.litres.bookstore.mapper.AutoAuthorMapper;
import com.litres.bookstore.mapper.AutoBookMapper;
import com.litres.bookstore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
            .map(AutoAuthorMapper.MAPPER::mapToAuthorDTO)
            .collect(Collectors.toList());
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
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        Author author = authorRepository.findById(authorId)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(authorId)));
        return bookRepository.findByAuthorId(author.getId()).stream()
            .map(AutoBookMapper.MAPPER::mapToBookDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByAuthorLogin(String login) {
        Author author = authorRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "login", login));
        return bookRepository.findByAuthorId(author.getId()).stream()
            .map(AutoBookMapper.MAPPER::mapToBookDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteAuthorById(Long id){
        authorRepository.deleteById(id);
    }
}
