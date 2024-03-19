package com.litres.bookstore.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.BookDTO;

@Service
public interface AuthorService {

    Page<AuthorDTO> getAllAuthors(Pageable pageable);

    AuthorDTO createAuthor(AuthorDTO author);

    AuthorDTO getAuthorById(Long id);

    AuthorDTO getAuthorByLogin(String login);

    Page<BookDTO> getBooksByAuthorId(Long authorId, Pageable pageable);

    Page<BookDTO> getBooksByAuthorLogin(String login, Pageable pageable);

    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthorById(Long id);
}
