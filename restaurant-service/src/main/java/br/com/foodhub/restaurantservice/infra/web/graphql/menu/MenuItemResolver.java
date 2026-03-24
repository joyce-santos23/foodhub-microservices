package br.com.foodhub.restaurantservice.infra.web.graphql.menu;

import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemResultDTO;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.items.CreateMenuItemUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.items.DeleteMenuItemUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.items.ListMenuItemUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.items.UpdateMenuItemUseCase;
import br.com.foodhub.restaurantservice.infra.web.mapper.MenuItemWebMapper;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.items.MenuItemRequestPayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.items.MenuItemResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

@Controller
@RequiredArgsConstructor
@EnableMethodSecurity
public class MenuItemResolver {

    private final CreateMenuItemUseCase createUseCase;
    private final UpdateMenuItemUseCase updateUseCase;
    private final DeleteMenuItemUseCase deleteUseCase;
    private final ListMenuItemUseCase listUseCase;

    private final MenuItemWebMapper mapper;

    // ======================
    // MUTATIONS
    // ======================

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public MenuItemResponsePayload createMenuItem(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId,
            @Argument String menuId,
            @Argument @Valid MenuItemRequestPayload input
    ) {

        MenuItemResultDTO result =
                createUseCase.execute(
                        requesterId,
                        restaurantId,
                        menuId,
                        mapper.toDto(input)
                );

        return mapper.toResponse(result);
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public MenuItemResponsePayload updateMenuItem(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId,
            @Argument String menuId,
            @Argument String menuItemId,
            @Argument @Valid MenuItemRequestPayload input
    ) {

        MenuItemResultDTO result =
                updateUseCase.execute(
                        requesterId,
                        restaurantId,
                        menuId,
                        menuItemId,
                        mapper.toDto(input)
                );

        return mapper.toResponse(result);
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public Boolean deleteMenuItem(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId,
            @Argument String menuId,
            @Argument String menuItemId
    ) {

        deleteUseCase.execute(requesterId, restaurantId, menuId, menuItemId);
        return true;
    }

    // ======================
    // QUERY
    // ======================

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public MenuItemResponsePayload menuItemById(
            @Argument String restaurantId,
            @Argument String menuId,
            @Argument String menuItemId
    ) {

        return mapper.toResponse(
                listUseCase.execute(restaurantId, menuId, menuItemId)
        );
    }
}
