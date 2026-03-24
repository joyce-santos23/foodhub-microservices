package br.com.foodhub.orderservice.core.application.dto.order;

import java.util.List;

public record CreateOrderRequestDTO(
        String restaurantId,
        String menuId,
        List<OrderItemRequestDTO> items
) {
}