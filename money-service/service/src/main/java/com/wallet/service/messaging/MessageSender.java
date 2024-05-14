package com.wallet.service.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.wallet.service.enums.MessageTypes;
import com.wallet.service.model.Message;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public void sendMessage(Message message) {
        jmsTemplate.convertAndSend("bookstore", message);
    }
}

