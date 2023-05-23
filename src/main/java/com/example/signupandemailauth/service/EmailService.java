package com.example.signupandemailauth.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailOTP(String to, String otp);
}
