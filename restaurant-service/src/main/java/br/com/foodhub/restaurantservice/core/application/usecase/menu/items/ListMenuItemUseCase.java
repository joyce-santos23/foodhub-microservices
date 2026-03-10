package br.com.foodhub.restaurantservice.core.application.usecase.menu.items;

import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.Menu;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.MenuItem;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListMenuItemUseCase {

    private final RestaurantGateway gateway;

    public MenuItemResultDTO execute(String restaurantId, String menuId, String itemId) {

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado com ID: " + restaurantId
                        )
                );

        Menu menu = restaurant.getMenuById(menuId);

        MenuItem item = menu.getItemById(itemId);

        return new MenuItemResultDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.isInRestaurantOnly(),
                item.getPhotograph()
        );
    }
}
