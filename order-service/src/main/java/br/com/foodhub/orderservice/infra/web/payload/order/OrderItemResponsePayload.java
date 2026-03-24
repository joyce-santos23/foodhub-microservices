package br.com.foodhub.orderservice.infra.web.payload.order;

import java.math.BigDecimal;

public record OrderItemResponsePayload(
        String productId,
        String name,
        Integer quantity,
        BigDecimal price
) {}
