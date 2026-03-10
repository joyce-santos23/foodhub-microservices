package br.com.foodhub.restaurantservice.core.application.port.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;

import java.util.Optional;

public interface RestaurantGateway {
    Restaurant save(Restaurant restaurant);

    PageResultDTO<Restaurant> findAll(int page, int size);

    Optional<Restaurant> findById(String restaurantId);

    void deleteById(String restaurantId);

    boolean existsByCnpj(String cnpj);
}
