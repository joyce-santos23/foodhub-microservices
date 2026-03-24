package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckUserRestaurantAccessUseCase {

    private final RestaurantGateway repository;

    public boolean execute(String userId, String restaurantId) {

        Restaurant restaurant = repository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        return restaurant.hasUser(userId);
    }
}
