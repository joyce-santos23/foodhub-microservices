package br.com.foodhub.restaurantservice.core.application.usecase.restaurant.openinghour;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour.OpeningHoursDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListOpeningHoursUseCase {

    private final RestaurantGateway restaurantGateway;

    public List<OpeningHoursDTO> execute(String restaurantId) {

        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com ID: " + restaurantId
                        )
                );

        return restaurant.getOpeningHours().stream()
                .map(oh -> new OpeningHoursDTO(
                        oh.getDayOfWeek(),
                        oh.getOpenTime(),
                        oh.getCloseTime(),
                        oh.isClosed()
                ))
                .toList();
    }
}
