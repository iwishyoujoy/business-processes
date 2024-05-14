package com.litres.bookstore.messaging;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public void sendMessage(String message) {
        jmsTemplate.convertAndSend("bookstore", message);
    }
}