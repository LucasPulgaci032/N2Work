package com.n2work.messages.messageModel;

import com.n2work.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prompt", nullable = false)
    private String prompt;

    @Column(name = "response")
    private String response;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //hibernate usa FetchType.LAZY, isso significa que ele busca apenas pelo id mesmo que User seja a classe inteira

}
