package com.litres.bookstore.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailGateway {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void sendEmail(String to, Letter letter) {
        Email email = new Email(to, letter);
        String json;
        try {
            json = objectMapper.writeValueAsString(email);

            jmsTemplate.convertAndSend("email-queue", json, message -> {
                message.setStringProperty("_type", "com.wallet.service.model.Email");
                return message;
            });
            System.out.println("Sending email to " + to + "...");
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
