package br.com.foodhub.contracts.event;

import java.math.BigDecimal;

public record OrderCreatedEventDTO(
        String orderId,
        String userId,
        BigDecimal amount,
        int attempts
) {}