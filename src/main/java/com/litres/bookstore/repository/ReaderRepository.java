package com.litres.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litres.bookstore.model.Reader;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByLogin(String login);
}
