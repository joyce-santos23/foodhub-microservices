package br.com.foodhub.restaurantservice.infra.web.payload.restaurant;


public record UpdateRestaurantRequestPayload(

        String businessName,
        String cuisineType,
        String numberStreet,
        String complement
) {
}
