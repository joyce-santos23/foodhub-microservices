package br.com.foodhub.orderservice.infra.web.payload.order;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderItemInput(
        @NotBlank
        String productId,
        @NotBlank
        Integer quantity
) {
}
