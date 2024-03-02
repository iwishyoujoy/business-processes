package com.litres.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litres.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
