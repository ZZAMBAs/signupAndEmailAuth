package com.example.signupandemailauth.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${auth.mail.id}")
    private String authId;
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmailOTP(String to, String otp){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.addRecipients(Message.RecipientType.TO, to);
            message.setSubject("이메일 OTP 알림");

            String msg = """
                    <div style='margin:20px;'>
                    <p>아래 코드를 복사해 입력해주세요<p>
                    <br>
                    <p>감사합니다.<p>
                    <br>
                    <div align='center' style='border:1px solid black; font-family:verdana';>
                    <h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>
                    <div style='font-size:130%'>
                    CODE : <strong>
                    """ + otp + """
                    </strong><div><br/>
                    </div>
                    """;

            message.setContent(msg, "text/html;charset=utf-8;");
            message.setFrom(authId);
        }
        catch (MessagingException e){
            throw new RuntimeException("sendEmailOTP Exception.", e);
        }
        mailSender.send(message);
    }
}
