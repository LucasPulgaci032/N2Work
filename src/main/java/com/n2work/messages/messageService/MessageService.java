package com.n2work.messages.messageService;

import com.n2work.Email.EmailService;
import com.n2work.exceptions.EmptyException;
import com.n2work.messages.messageDTO.MessageDTO;
import com.n2work.messages.messageModel.Message;
import com.n2work.messages.messageRepository.MessageRepository;
import com.n2work.user.model.User;
import com.n2work.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final EmailService emailService;

    public MessageService(MessageRepository messageRepository, UserService userService, EmailService emailService){
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    public List<Message> findMessages(){
        return messageRepository.findAll();
    }

    public Message postMessage(MessageDTO body){
            if(body.message() == null || body.message().isEmpty()){
                throw new EmptyException("Mensagem inválida");
            }
           User user = userService.findUserById(body.userId());
           Message msg = new Message();
            msg.setSubject(body.subject());
            msg.setMessage(body.message());
            msg.setUser(user);

             Message savedMessage = messageRepository.save(msg);

             String recipient = "lucaspulgaci7@gmail.com";

             String emailSubject = user.getEmail();
             String emailBody = body.message();
             MessageDTO emailDTO = new MessageDTO(emailSubject, emailBody, user.getId());
             emailService.sendEmail(recipient, emailDTO);

             return savedMessage;

    }
}
