package com.litres.bookstore.messaging;

import org.springframework.jms.annotation.JmsListener;

import com.litres.bookstore.model.Message;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageReceiver {

    @JmsListener(destination = "bookstore")
    public void receiveMessage(Message message) {
        System.out.println("Received message: " + message);
    }
}
