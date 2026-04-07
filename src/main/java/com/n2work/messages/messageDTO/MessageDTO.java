package com.n2work.messages.messageDTO;

public record MessageDTO(
        String subject,
        String prompt,
        String response,
        Long userId
) {}
