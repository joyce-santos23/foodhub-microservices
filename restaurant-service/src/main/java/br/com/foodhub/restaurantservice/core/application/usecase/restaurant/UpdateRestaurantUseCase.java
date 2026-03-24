package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.UpdateRestaurantDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateRestaurantUseCase {

    private final RestaurantGateway gateway;

    public RestaurantResultDTO execute(
            String userId,
            String restaurantId,
            UpdateRestaurantDTO dto
    ) {
        log.info("Iniciando processo de atualização de restaurante: {} input: {}", restaurantId, dto);

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com o ID: " + restaurantId
                        )
                );

        restaurant.ensureUserCanManage(userId);

        restaurant.updateBasicInfo(
                dto.businessName(),
                dto.cuisineType(),
                dto.numberStreet(),
                dto.complement()
        );

        gateway.save(restaurant);

        return RestaurantResultDTO.from(restaurant);
    }
}
