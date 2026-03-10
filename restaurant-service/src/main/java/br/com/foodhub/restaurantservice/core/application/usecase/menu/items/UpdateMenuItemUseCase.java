package br.com.foodhub.restaurantservice.core.application.usecase.menu.items;

import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemRequestDTO;
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
public class UpdateMenuItemUseCase {

    private final RestaurantGateway gateway;

    public MenuItemResultDTO execute(
            String userId,
            String restaurantId,
            String menuId,
            String menuItemId,
            MenuItemRequestDTO dto) {

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        restaurant.ensureUserCanManage(userId);

        Menu menu = restaurant.getMenuById(menuId);

        MenuItem item = menu.getItemById(menuItemId);
        item.update(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.inRestaurantOnly(),
                dto.photograph()
        );
        gateway.save(restaurant);

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
