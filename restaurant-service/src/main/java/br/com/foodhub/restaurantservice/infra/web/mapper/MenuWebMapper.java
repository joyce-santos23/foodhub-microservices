package br.com.foodhub.restaurantservice.infra.web.mapper;


import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuWithItemsResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.CreateMenuRequestPayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.MenuResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.MenuWithItemsResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.items.MenuItemResponsePayload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuWebMapper {

    // ======================
    // REQUEST → DTO
    // ======================

    public MenuRequestDTO toCreateDto(CreateMenuRequestPayload payload) {
        if (payload == null) return null;

        return new MenuRequestDTO(
                payload.name()
        );
    }

    // ======================
    // DTO → RESPONSE (simples)
    // ======================

    public MenuResponsePayload toResponse(MenuResultDTO dto) {
        if (dto == null) return null;

        return new MenuResponsePayload(
                dto.id(),
                dto.name()
        );
    }

    // ======================
    // DTO → RESPONSE (com items)
    // ======================

    public MenuWithItemsResponsePayload toWithItemsResponse(MenuWithItemsResultDTO dto) {
        if (dto == null) return null;

        List<MenuItemResponsePayload> items = dto.items() == null
                ? List.of()
                : dto.items().stream()
                .map(this::toItemResponse)
                .toList();

        return new MenuWithItemsResponsePayload(
                dto.menuId(),
                dto.name(),
                items
        );
    }

    // ======================
    // ITEM MAPPING
    // ======================

    private MenuItemResponsePayload toItemResponse(MenuItemResultDTO item) {
        if (item == null) return null;

        return new MenuItemResponsePayload(
                item.id(),
                item.name(),
                item.description(),
                item.price(),
                item.inRestaurantOnly(),
                item.photograph()
        );
    }
}
