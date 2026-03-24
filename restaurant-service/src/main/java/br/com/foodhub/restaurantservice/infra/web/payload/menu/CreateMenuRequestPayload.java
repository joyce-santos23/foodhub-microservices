package br.com.foodhub.restaurantservice.infra.web.payload.menu;

import jakarta.validation.constraints.NotBlank;

public record CreateMenuRequestPayload(

        @NotBlank
        String name
) {}
