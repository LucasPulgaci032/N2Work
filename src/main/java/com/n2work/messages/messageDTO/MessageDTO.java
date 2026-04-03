package com.n2work.messages.messageDTO;

public record MessageDTO(
        String subject,
        String message,
        Long userId
) {}
