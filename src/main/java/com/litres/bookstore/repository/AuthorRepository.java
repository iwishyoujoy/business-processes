package com.litres.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litres.bookstore.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByLogin(String login);
}
