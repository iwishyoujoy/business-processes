package com.litres.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litres.bookstore.model.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Reader findByLogin(String login);
}
