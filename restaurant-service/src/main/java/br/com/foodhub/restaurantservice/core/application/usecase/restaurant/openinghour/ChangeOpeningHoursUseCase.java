package br.com.foodhub.restaurantservice.core.application.usecase.restaurant.openinghour;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
public class ChangeOpeningHoursUseCase {

    private final RestaurantGateway restaurantGateway;

    public void execute(
            String userId,
            String restaurantId,
            DayOfWeek dayOfWeek,
            UpdateOpeningHoursDTO dto
    ) {

        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurante não encontrado")
                );

        restaurant.ensureUserCanManage(userId);

        restaurant.changeOpeningHours(
                dayOfWeek,
                dto.openingTime(),
                dto.closingTime(),
                dto.closed()
        );

        restaurantGateway.save(restaurant);
    }
}
