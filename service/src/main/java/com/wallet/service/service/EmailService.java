package com.wallet.service.service;

import org.springframework.stereotype.Service;

import com.wallet.service.model.Email;

@Service
public class EmailService {

    public void sendEmail(Email email) {
        try {
            System.out.println("Letter \"" + email.getLetter().getName() + "\" was sent to " + email.getTo() + ". With subject: " + email.getLetter().getSubject());
        } catch (Exception e) {
            // Handle exception
        }
    }
}