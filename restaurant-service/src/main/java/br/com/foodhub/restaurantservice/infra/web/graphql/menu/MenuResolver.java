package br.com.foodhub.restaurantservice.infra.web.graphql.menu;

import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.menu.MenuWithItemsResultDTO;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.CreateMenuUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.DeleteMenuUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.ListRestaurantMenuUseCase;
import br.com.foodhub.restaurantservice.infra.web.mapper.MenuWebMapper;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.CreateMenuRequestPayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.MenuResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.menu.MenuWithItemsResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@EnableMethodSecurity
public class MenuResolver {

    private final CreateMenuUseCase createMenuUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;
    private final ListRestaurantMenuUseCase listMenuUseCase;

    private final MenuWebMapper mapper;

    // ======================
    // MUTATIONS
    // ======================

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public MenuResponsePayload createMenu(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId,
            @Argument @Valid CreateMenuRequestPayload input
    ) {

        MenuResultDTO result =
                createMenuUseCase.execute(
                        requesterId,
                        restaurantId,
                        mapper.toCreateDto(input)
                );

        return mapper.toResponse(result);
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public Boolean deleteMenu(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId,
            @Argument String menuId
    ) {

        deleteMenuUseCase.execute(requesterId, restaurantId, menuId);
        return true;
    }

    // ======================
    // QUERIES
    // ======================

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<MenuWithItemsResponsePayload> listRestaurantMenus(
            @Argument String restaurantId
    ) {

        List<MenuWithItemsResultDTO> result =
                listMenuUseCase.execute(restaurantId);

        return result.stream()
                .map(mapper::toWithItemsResponse)
                .toList();
    }
}