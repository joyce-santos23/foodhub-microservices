package br.com.foodhub.restaurantservice.infra.web.payload.menu.items;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MenuItemRequestPayload(
        @NotBlank
        String name,
        String description,

        @NotNull
        BigDecimal price,
        boolean inRestaurantOnly,
        String photograph
) {
}
