package com.tech.calendar.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPassword(String toEmail,
                             String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("squad1.orangej@gmail.com");
        message.setTo(toEmail);
        message.setText("Você solicitou um lembrete da sua senha. http://localhost:8080/auth/resetar-senha/" + token);
        message.setSubject("Recuperação de senha");

        mailSender.send(message);
    }
}
