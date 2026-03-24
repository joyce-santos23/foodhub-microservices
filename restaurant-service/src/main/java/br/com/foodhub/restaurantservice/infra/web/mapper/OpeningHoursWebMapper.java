package br.com.foodhub.restaurantservice.infra.web.mapper;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour.OpeningHoursDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour.OpeningHoursResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour.UpdateOpeningHoursInput;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class OpeningHoursWebMapper {

    public UpdateOpeningHoursDTO toDto(UpdateOpeningHoursInput payload) {

        if (payload.closed()) {
            return new UpdateOpeningHoursDTO(
                    null,
                    null,
                    true
            );
        }

        return new UpdateOpeningHoursDTO(
                LocalTime.parse(payload.openingTime()),
                LocalTime.parse(payload.closingTime()),
                payload.closed()
        );
    }

    public OpeningHoursResponsePayload toResponse(OpeningHoursDTO dto) {
        return new OpeningHoursResponsePayload(
                dto.dayOfWeek(),
                dto.openTime() != null ? dto.openTime().toString() : null,
                dto.closeTime() != null ? dto.closeTime().toString() : null,
                dto.closed()
        );
    }
}
