package com.litres.bookstore.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;

@Service
public interface AuthorService {

    Page<AuthorDTO> getAllAuthors(Pageable pageable);

    AuthorDTO createAuthor(AuthorDTO author);

    AuthorDTO getLoggedAuthor();

    AuthorDTO getAuthorById(Long id);

    AuthorDTO getAuthorByLogin(String login);

    Page<BookDTO> getBooks(Pageable pageable);

    Page<BookDTO> getBooksById(Long authorId, Pageable pageable);

    Optional<AuthorDTO> updateAuthor(Map<String, Object> updates);

    void deleteAuthor();
}
