package br.com.foodhub.restaurantservice.infra.web.payload.restaurant;


import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour.OpeningHoursResponsePayload;

import java.util.List;

public record RestaurantResponsePayload(

        String restaurantId,
        String businessName,
        String cnpj,
        String cuisineType,
        String ownerId,
        String numberStreet,
        String complement,
        String addressBaseId,
        List<OpeningHoursResponsePayload> openingHours
) {}
