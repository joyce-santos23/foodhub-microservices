package br.com.foodhub.restaurantservice.infra.web.payload.menu.items;

import java.math.BigDecimal;

public record MenuItemResponsePayload(

        String id,
        String name,
        String description,
        BigDecimal price,
        boolean inRestaurantOnly,
        String photograph
) {}
