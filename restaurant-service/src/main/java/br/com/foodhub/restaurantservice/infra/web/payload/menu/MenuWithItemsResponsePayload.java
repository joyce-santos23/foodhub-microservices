package br.com.foodhub.restaurantservice.infra.web.payload.menu;

import br.com.foodhub.restaurantservice.infra.web.payload.menu.items.MenuItemResponsePayload;

import java.util.List;


public record MenuWithItemsResponsePayload(

        String menuId,
        String name,
        List<MenuItemResponsePayload> items
) {}
