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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<Book> getBooksForUserByLogin(String userLogin) {
        User user = userRepository.findByLogin(userLogin);
        if (user != null) {
            return user.getBooks();
        }
        return null;
    }

    public List<Book> getBooksForUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        if (user != null) {
            return user.getBooks();
        }
        return null;
    }

    public void addBookToUser(String userLogin, Long bookId) {
        User user = userRepository.findByLogin(userLogin);
        if (user != null) {
            Book book = new Book();
            book.setId(bookId);
            user.getBooks().add(book);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    // Добавьте другие методы для обновления, удаления книги и т.д.
}