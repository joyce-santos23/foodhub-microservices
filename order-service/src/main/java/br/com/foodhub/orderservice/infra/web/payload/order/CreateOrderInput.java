package br.com.foodhub.orderservice.infra.web.payload.order;

import br.com.foodhub.orderservice.core.application.dto.order.OrderItemRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderInput(
        @NotBlank
        String restaurantId,
        @NotBlank
        String menuId,
        @NotEmpty
        List<OrderItemRequestDTO> items
) {
}
