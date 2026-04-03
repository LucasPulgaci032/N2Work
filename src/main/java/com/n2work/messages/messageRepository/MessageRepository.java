package com.n2work.messages.messageRepository;

import com.n2work.messages.messageModel.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
