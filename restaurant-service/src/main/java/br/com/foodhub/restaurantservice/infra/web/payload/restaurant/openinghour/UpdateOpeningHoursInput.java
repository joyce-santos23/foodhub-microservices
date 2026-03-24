package br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour;


public record UpdateOpeningHoursInput(

        String openingTime,
        String closingTime,
        boolean closed
) {}
