package com.litres.bookstore.messaging;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.litres.bookstore.enums.MessageTypes;
import com.litres.bookstore.model.Message;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public void sendMessage(Message message) {
        jmsTemplate.convertAndSend("bookstore", message);
    }

    @Transactional
    public void sendDeleteMessage(Long userId){
        Message message = new Message();
        message.setType(MessageTypes.DELETE);
        message.setAuthorId(userId);
        sendMessage(message);
    }

    @Transactional
    public void sendCreateMessage(Long userId, Float amount){
        Message message = new Message();
        message.setType(MessageTypes.CREATE);
        message.setAuthorId(userId);
        message.setMoney(amount);
        sendMessage(message);
    }

    @Transactional
    public void sendCheckoutAuthorMessage(Long authorId, Float amount){
        Message message = new Message();
        message.setType(MessageTypes.CHECKOUT_AUTHOR);
        message.setAuthorId(authorId);
        message.setMoney(amount);
        sendMessage(message);
    }

    @Transactional
    public void sendCheckoutAuthorAndReaderMessage(Long authorId, Long readerId, Float amount){
        Message message = new Message();
        message.setType(MessageTypes.CHECKOUT_READER);
        message.setAuthorId(authorId);
        message.setReaderId(readerId);
        message.setMoney(amount);
        sendMessage(message);
    }
}