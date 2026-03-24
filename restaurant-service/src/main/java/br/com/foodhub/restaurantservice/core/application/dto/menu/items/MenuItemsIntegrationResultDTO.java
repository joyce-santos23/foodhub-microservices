package br.com.foodhub.restaurantservice.core.application.dto.menu.items;

import java.math.BigDecimal;

public record MenuItemsIntegrationResultDTO(
        String id,
        String name,
        BigDecimal price
) {
}
