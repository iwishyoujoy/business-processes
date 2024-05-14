package com.wallet.service.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public void sendMessage(String message) {
        jmsTemplate.convertAndSend("bookstore", message);
    }
}

