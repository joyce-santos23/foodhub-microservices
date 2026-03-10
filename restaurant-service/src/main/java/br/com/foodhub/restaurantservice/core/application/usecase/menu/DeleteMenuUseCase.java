package br.com.foodhub.restaurantservice.core.application.usecase.menu;

import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuUseCase {

    private final RestaurantGateway gateway;

    public void execute(String userId, String restaurantId, String menuId) {

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com o ID: " + restaurantId
                        )
                );

        restaurant.ensureUserCanManage(userId);

        restaurant.removeMenu(menuId);

        gateway.save(restaurant);
    }
}
