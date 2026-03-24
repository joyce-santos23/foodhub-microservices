package br.com.foodhub.orderservice.core.application.dto.order;

import br.com.foodhub.orderservice.core.domain.entity.order.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResultDTO(
        String id,
        String userId,
        String restaurantId,
        BigDecimal totalAmount,
        String status,
        LocalDateTime createdAt,
        List<OrderItemResultDTO> items
) {

    public static OrderResultDTO from(Order order) {

        return new OrderResultDTO(
                order.getId(),
                order.getUserId(),
                order.getRestaurantId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(OrderItemResultDTO::from)
                        .toList()
        );
    }
}