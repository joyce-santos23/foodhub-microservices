package br.com.foodhub.orderservice.core.application.dto.order;


public record OrderItemRequestDTO(
        String productId,
        Integer quantity
) {
}