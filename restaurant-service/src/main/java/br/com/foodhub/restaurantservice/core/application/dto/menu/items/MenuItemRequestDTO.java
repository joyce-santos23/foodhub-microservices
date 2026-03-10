package br.com.foodhub.restaurantservice.core.application.dto.menu.items;

import java.math.BigDecimal;

public record MenuItemRequestDTO(
        String name,
        String description,
        BigDecimal price,
        boolean inRestaurantOnly,
        String photograph
) {
}
