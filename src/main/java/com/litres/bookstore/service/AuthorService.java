package com.litres.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.litres.bookstore.repository.AuthorRepository;
import com.litres.bookstore.model.Author;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    public Author getAuthorByLogin(String login) {
        return authorRepository.findByLogin(login);
    }
}