package br.com.foodhub.orderservice.core.application.dto.restaurant;

import java.math.BigDecimal;

public record MenuItemDTO(
        String id,
        String name,
        BigDecimal price
) {}
