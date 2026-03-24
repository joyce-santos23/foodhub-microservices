package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListRestaurantByIdUseCase {

    private final RestaurantGateway gateway;

    public RestaurantResultDTO execute(String restaurantId) {
        log.info("Iniciando busca de restaurante por id: " + restaurantId);

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado com o ID: " + restaurantId));

        return RestaurantResultDTO.from(restaurant);

    }
}
