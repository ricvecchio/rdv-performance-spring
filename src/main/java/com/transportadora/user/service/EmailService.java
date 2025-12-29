package com.transportadora.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(String email, String subject, String body) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            return "E-mail enviado!";
        } catch (Exception e) {
            return "Erro ao tentar enviar o e-mail " + e.getLocalizedMessage();
        }
    }

}
