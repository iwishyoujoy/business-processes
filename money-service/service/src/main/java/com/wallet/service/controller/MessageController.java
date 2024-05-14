package com.wallet.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.service.messaging.MessageSender;

@RestController
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable("message") String message) {
        messageSender.sendMessage(message);
        return message;
    }
}
