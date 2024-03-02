package com.litres.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litres.bookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
