package br.com.foodhub.restaurantservice.core.application.usecase.menu.items;

import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemsIntegrationResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.Menu;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.MenuItem;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListMenuItemsIntegrationUseCase {

    private final RestaurantGateway gateway;

    public List<MenuItemsIntegrationResultDTO> execute(
            String restaurantId,
            String menuId,
            List<String> itemIds
    ) {

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurante não encontrado: " + restaurantId
                        )
                );

        Menu menu = restaurant.getMenuById(menuId);

        return itemIds.stream()
                .map(menu::getItemById)
                .map(this::map)
                .toList();
    }

    private MenuItemsIntegrationResultDTO map(MenuItem item) {

        return new MenuItemsIntegrationResultDTO(
                item.getId(),
                item.getName(),
                item.getPrice()
        );
    }
}

