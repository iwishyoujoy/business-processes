package com.service.emails.service;

import org.springframework.stereotype.Service;

import com.service.emails.model.Email;

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