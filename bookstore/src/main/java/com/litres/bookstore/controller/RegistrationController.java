package com.litres.bookstore.controller;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.mapper.UserMapper;
import com.litres.bookstore.messaging.EmailGateway;
import com.litres.bookstore.messaging.Letter;
import com.litres.bookstore.model.User;
import com.litres.bookstore.model.WalletRequest;
import com.litres.bookstore.service.AuthorService;
import com.litres.bookstore.service.ReaderService;
import com.litres.bookstore.service.WalletService;
import com.litres.bookstore.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs for Registration",
        description = "REST APIs - Registration Author, Registration Reader"
)
@RestController
class RegistrationController {   

    private final UserServiceImpl userService;

    private final AuthorService authorService;

    private final ReaderService readerService;

    private final WalletService walletService;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private final EmailGateway emailGateway;

    public RegistrationController(UserServiceImpl userService, AuthorService authorService, ReaderService readerService, UserMapper userMapper, AuthenticationManager authenticationManager, WalletService walletService) {
        this.userService = userService;
        this.authorService = authorService;
        this.readerService = readerService;
        this.walletService = walletService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.emailGateway = new EmailGateway();
    }

    @Operation(
            summary = "Create Author"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/api/authors/registration")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO author) {
        if (!userService.saveUser(userMapper.mapToUser(author))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User savedUser = userService.findUserByUsername(author.getLogin());
        author.setId(savedUser.getId());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(author.getLogin(), author.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Letter letter = new Letter("Welcome!", "Nice to see you in our team!", "You've just registered as author on Litres!");
        emailGateway.sendEmail(author.getEmail(), letter);

        walletService.createWallet(new WalletRequest(savedUser.getId(), author.getMoney()));

        return new ResponseEntity<>(authorService.createAuthor(author), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create Reader"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/api/readers/registration")
    public ResponseEntity<ReaderDTO> createReader(@RequestBody ReaderDTO reader) {
        if (!userService.saveUser(userMapper.mapToUser(reader))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User savedUser = userService.findUserByUsername(reader.getLogin());
        reader.setId(savedUser.getId());
        
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(reader.getLogin(), reader.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Letter letter = new Letter("Welcome!", "Nice to see you here!", "You've just registered as reader on Litres!");
        emailGateway.sendEmail(reader.getEmail(), letter);

        walletService.createWallet(new WalletRequest(savedUser.getId(), reader.getMoney()));

        return new ResponseEntity<>(readerService.createReader(reader), HttpStatus.CREATED);
    }

}