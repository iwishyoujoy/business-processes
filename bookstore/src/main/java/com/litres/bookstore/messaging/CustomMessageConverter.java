package com.litres.bookstore.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class CustomMessageConverter implements MessageConverter {
    private static final Logger logger = LoggerFactory.getLogger(CustomMessageConverter.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!(object instanceof com.litres.bookstore.model.Message)) {
            throw new IllegalArgumentException("Expected instance of com.litres.bookstore.model.Message");
        }
    
        com.litres.bookstore.model.Message myMessage = (com.litres.bookstore.model.Message) object;
        try {
            String jsonPayload = mapper.writeValueAsString(myMessage);
            TextMessage textMessage = session.createTextMessage(jsonPayload);
            return textMessage;
        } catch (Exception e) {
            logger.error("Error converting message to JMS message", e);
            throw new MessageConversionException("Failed to convert message to JMS message", e);
        }
    }
    

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        com.litres.bookstore.model.Message myMessage = null;
        try {
            myMessage = mapper.readValue(payload, com.litres.bookstore.model.Message.class);
        } catch (Exception e) {
            logger.error("error converting to message", e);
        }

        return myMessage;
    }
}