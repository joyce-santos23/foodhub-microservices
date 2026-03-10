package br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour;

import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.OpeningHours;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningHoursDTO(
        DayOfWeek dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        boolean closed
) {
    public static OpeningHoursDTO from(OpeningHours openingHours) {
        return new OpeningHoursDTO(
                openingHours.getDayOfWeek(),
                openingHours.getOpenTime(),
                openingHours.getCloseTime(),
                openingHours.isClosed()
        );
    }
}

