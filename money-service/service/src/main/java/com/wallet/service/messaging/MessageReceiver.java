package com.wallet.service.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

@Configuration
public class MessageReceiver {

    @JmsListener(destination = "bookstore")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
