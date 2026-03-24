package br.com.foodhub.orderservice.core.application.dto.order;

import br.com.foodhub.orderservice.core.domain.entity.order.OrderItem;

import java.math.BigDecimal;

public record OrderItemResultDTO(
        String productId,
        String name,
        Integer quantity,
        BigDecimal price
) {
    public static OrderItemResultDTO from(OrderItem item) {
        return new OrderItemResultDTO(
                item.getProductId(),
                item.getName(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}
