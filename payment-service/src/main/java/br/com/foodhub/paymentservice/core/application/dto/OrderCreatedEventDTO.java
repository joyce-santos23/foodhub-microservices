package br.com.foodhub.paymentservice.core.application.dto;

import java.math.BigDecimal;

public record OrderCreatedEventDTO(
        String orderId,
        String userId,
        BigDecimal amount,
        int attempts
) {
}
