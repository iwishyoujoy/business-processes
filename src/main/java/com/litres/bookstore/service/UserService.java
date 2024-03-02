package com.litres.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litres.bookstore.repository.UserRepository;
import com.litres.bookstore.model.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Добавьте другие методы для обновления, удаления книги и т.д.
}