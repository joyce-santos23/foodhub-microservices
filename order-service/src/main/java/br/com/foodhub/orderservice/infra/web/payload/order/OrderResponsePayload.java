package br.com.foodhub.orderservice.infra.web.payload.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponsePayload(
        String id,
        String userId,
        String restaurantId,
        String status,
        BigDecimal totalAmount,
        List<OrderItemResponsePayload> items
) {}
