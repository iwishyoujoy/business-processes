package com.litres.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litres.bookstore.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
