package com.litres.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litres.bookstore.service.*;
import com.litres.bookstore.model.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private BookService bookService;

}
