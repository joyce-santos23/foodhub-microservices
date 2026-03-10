package br.com.foodhub.restaurantservice.core.application.dto.menu.items;

import br.com.foodhub.restaurantservice.core.domain.entity.menu.MenuItem;

import java.math.BigDecimal;

public record MenuItemResultDTO(
        String id,
        String name,
        String description,
        BigDecimal price,
        boolean inRestaurantOnly,
        String photograph
) {

    public static MenuItemResultDTO from(MenuItem item) {
        return new MenuItemResultDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.isInRestaurantOnly(),
                item.getPhotograph()
        );
    }
}
