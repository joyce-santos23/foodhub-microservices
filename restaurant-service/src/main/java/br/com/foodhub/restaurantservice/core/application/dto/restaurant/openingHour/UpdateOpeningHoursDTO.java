package br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour;

import java.time.LocalTime;

public record UpdateOpeningHoursDTO(
        LocalTime openingTime,
        LocalTime closingTime,
        boolean closed
) {}

