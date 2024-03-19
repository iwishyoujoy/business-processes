package com.litres.bookstore.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;
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
    public Page<BookDTO> getBooksByAuthorId(Long authorId, Pageable pageable) {
        Author author = authorRepository.findById(authorId)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(authorId)));
        return bookRepository.findByAuthorId(author.getId(), pageable).map(book -> bookMapper.mapToBookDTO(book));
    }

    @Override
    public Page<BookDTO> getBooksByAuthorLogin(String login, Pageable pageable) {
        Author author = authorRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Author", "login", login));
        return bookRepository.findByAuthorId(author.getId(), pageable).map(book -> bookMapper.mapToBookDTO(book));
    }

    @Override
    public void deleteAuthorById(Long id){
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            authorMapper.mapToUpdatedAuthor(authorDTO, author);
            Author updatedAuthor = authorRepository.save(author);
            return authorMapper.mapToAuthorDTO(updatedAuthor);
        } else {
            throw new ResourceNotFoundException("Author", "id", String.valueOf(id));
        }
    }
}
