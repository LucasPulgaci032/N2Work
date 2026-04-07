package com.n2work.messages.messageService;

import com.n2work.AI.Ollama.OllamaService.OllamaService;
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
    private final OllamaService ollamaService;

    public MessageService(MessageRepository messageRepository, UserService userService, EmailService emailService, OllamaService ollamaService){
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.emailService = emailService;
        this.ollamaService = ollamaService;
    }

    public List<Message> findMessages(){
        return messageRepository.findAll();
    }

    public Message postMessage(MessageDTO body){

        if(body.prompt() == null || body.prompt().isEmpty()){
            throw new EmptyException("Mensagem inválida");
        }

        User user = userService.findUserById(body.userId());

        String response = ollamaService.generateResponse(body.prompt());

        Message msg = new Message();
        msg.setPrompt(body.prompt());
        msg.setResponse(response);
        msg.setUser(user);

        Message savedMessage = messageRepository.save(msg);

        String recipient = "lucaspulgaci7@gmail.com";
        String emailSubject = "Nova interação - " + user.getEmail();

        MessageDTO emailDTO = new MessageDTO(
                emailSubject,
                body.prompt(),
                body.response(),
                user.getId()
        );

        emailService.sendEmail(recipient, emailDTO);

        return savedMessage;
    }
}
