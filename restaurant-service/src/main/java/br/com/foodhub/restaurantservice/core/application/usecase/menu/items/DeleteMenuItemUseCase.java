package br.com.foodhub.restaurantservice.core.application.usecase.menu.items;

import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.Menu;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuItemUseCase {

    private final RestaurantGateway gateway;

    public void execute(
            String userId,
            String restaurantId,
            String menuId,
            String menuItemId
    ) {


        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        restaurant.ensureUserCanManage(userId);

        Menu menu = restaurant.getMenuById(menuId);
        menu.removeItem(menuItemId);
        gateway.save(restaurant);
    }
}
