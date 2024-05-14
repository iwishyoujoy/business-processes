package com.litres.bookstore.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageReceiver {

    @JmsListener(destination = "bookstore")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
