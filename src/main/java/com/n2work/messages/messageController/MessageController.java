package com.n2work.messages.messageController;


import com.n2work.messages.messageDTO.MessageDTO;
import com.n2work.messages.messageModel.Message;
import com.n2work.messages.messageService.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173",
        allowedHeaders = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping("")
    public List<Message> findMessages(){
        return messageService.findMessages();
    }

    @PostMapping("")
    public Message postMessage(@RequestBody MessageDTO body){
       return messageService.postMessage(body);

    }
}
