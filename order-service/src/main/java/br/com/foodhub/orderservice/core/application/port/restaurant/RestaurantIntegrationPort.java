package br.com.foodhub.orderservice.core.application.port.restaurant;

import br.com.foodhub.orderservice.core.application.dto.restaurant.MenuItemDTO;
import java.util.List;

public interface RestaurantIntegrationPort {

    void validateRestaurant(String restaurantId);

    List<MenuItemDTO> findMenuItems(
            String restaurantId,
            String menuId,
            List<String> itemIds);

    boolean isUserLinkedToRestaurant(String userId, String restaurantId);

}
