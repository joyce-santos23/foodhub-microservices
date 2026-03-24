package br.com.foodhub.restaurantservice.infra.web.mapper;

import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.items.MenuItemRequestPayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.items.MenuItemResponsePayload;
import org.springframework.stereotype.Component;

@Component
public class MenuItemWebMapper {

    // ======================
    // REQUEST → DTO
    // ======================

    public MenuItemRequestDTO toDto(MenuItemRequestPayload payload) {
        if (payload == null) return null;

        return new MenuItemRequestDTO(
                payload.name(),
                payload.description(),
                payload.price(),
                payload.inRestaurantOnly(),
                payload.photograph()
        );
    }

    // ======================
    // DTO → RESPONSE
    // ======================

    public MenuItemResponsePayload toResponse(MenuItemResultDTO dto) {
        if (dto == null) return null;

        return new MenuItemResponsePayload(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.inRestaurantOnly(),
                dto.photograph()
        );
    }
}
