package com.litres.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.litres.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    
    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.login = :authorLogin")
    List<Book> findByAuthorLogin(String authorLogin);

    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.id = :authorId")
    List<Book> findByAuthorId(Long authorId);
}
