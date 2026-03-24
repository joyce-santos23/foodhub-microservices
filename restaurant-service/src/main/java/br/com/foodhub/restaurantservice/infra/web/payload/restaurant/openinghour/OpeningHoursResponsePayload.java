package br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour;


import java.time.DayOfWeek;

public record OpeningHoursResponsePayload(

        DayOfWeek dayOfWeek,
        String openingTime,
        String closingTime,
        boolean closed
) {}
