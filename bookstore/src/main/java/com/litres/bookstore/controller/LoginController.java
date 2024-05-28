package com.litres.bookstore.controller;

import com.litres.bookstore.dto.UserDTO;
import com.litres.bookstore.messaging.EmailGateway;
import com.litres.bookstore.messaging.Letter;
import com.litres.bookstore.model.User;
import com.litres.bookstore.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(
        name = "REST APIs for Login",
        description = "REST APIs - Login Users"
)
@RestController
public class LoginController {
    private final UserRepository userRepository;
    @Autowired
    private final EmailGateway emailGateway;

    private final AuthenticationManager authenticationManager;

    public LoginController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.emailGateway = new EmailGateway();
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/api/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Optional<User> currentUser = userRepository.findByUsername(user.getLogin());
            if (currentUser.isPresent()) {
                User loggedInUser = currentUser.get();
                Letter letter = new Letter("Security message", "Was it you?", "Someone logged in to your account on Litres");
                emailGateway.sendEmail(loggedInUser.getEmail(), letter);
            }
            
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthenticationException ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}