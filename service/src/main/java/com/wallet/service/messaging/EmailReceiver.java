package com.wallet.service.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.context.annotation.Configuration;

import com.wallet.service.model.Email;
import com.wallet.service.service.EmailService;

@Configuration
public class EmailReceiver {

    private final EmailService emailService;

    public EmailReceiver(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "email-queue")
    public void receiveMessage(Email email) {
        System.out.println("Got request to send an email");
        emailService.sendEmail(email);
    }
}
