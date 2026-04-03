package com.n2work.Email;

import com.n2work.messages.messageDTO.MessageDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;

    }

    public void sendEmail(String to, MessageDTO messageDTO){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(messageDTO.subject());
        message.setText(messageDTO.message());
        mailSender.send(message);
    }
}
