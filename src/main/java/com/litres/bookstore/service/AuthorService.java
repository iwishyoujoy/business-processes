package com.litres.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;

@Service
public interface AuthorService {

    List<AuthorDTO> getAllAuthors();

    AuthorDTO createAuthor(AuthorDTO author);

    AuthorDTO getAuthorById(Long id);

    AuthorDTO getAuthorByLogin(String login);

    List<BookDTO> getBooksByAuthorId(Long authorId);

    List<BookDTO> getBooksByAuthorLogin(String login);

    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthorById(Long id);
}
