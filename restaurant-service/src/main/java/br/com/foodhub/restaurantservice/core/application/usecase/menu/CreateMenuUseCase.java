package br.com.foodhub.restaurantservice.core.application.usecase.menu;

import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.Menu;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuUseCase {

    private final RestaurantGateway gateway;

    public MenuResultDTO execute(
            String userId,
            String restaurantId,
            MenuRequestDTO dto
    ) {

        Restaurant restaurant = gateway.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurante não encontrado")
                );

        restaurant.ensureUserCanManage(userId);

        Menu menu = new Menu(dto.name());

        restaurant.addMenu(menu);

        gateway.save(restaurant);

        return new MenuResultDTO(
                menu.getId(),
                menu.getName()
        );
    }
}
