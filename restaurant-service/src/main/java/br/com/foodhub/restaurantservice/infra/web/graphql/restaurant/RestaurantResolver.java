package br.com.foodhub.restaurantservice.infra.web.graphql.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.AddressBaseDTO;
import br.com.foodhub.restaurantservice.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour.UpdateOpeningHoursDTO;
import br.com.foodhub.restaurantservice.core.application.port.address.AddressIntegrationPort;
import br.com.foodhub.restaurantservice.core.application.usecase.restaurant.*;
import br.com.foodhub.restaurantservice.core.application.usecase.restaurant.openinghour.ChangeOpeningHoursUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.restaurant.openinghour.ListOpeningHoursUseCase;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantRole;
import br.com.foodhub.restaurantservice.infra.web.mapper.OpeningHoursWebMapper;
import br.com.foodhub.restaurantservice.infra.web.mapper.RestaurantWebMapper;
import br.com.foodhub.restaurantservice.infra.web.payload.address.AddressBaseResponse;
import br.com.foodhub.restaurantservice.infra.web.payload.pagination.PageResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.RestaurantRequestInput;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.RestaurantResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.UpdateRestaurantRequestPayload;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour.OpeningHoursResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.openinghour.UpdateOpeningHoursInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@EnableMethodSecurity
public class RestaurantResolver {

    private final CreateRestaurantUseCase createUseCase;
    private final UpdateRestaurantUseCase updateUseCase;
    private final DeleteRestaurantUseCase deleteUseCase;
    private final ListAllRestaurantUseCase listAllUseCase;
    private final ListRestaurantByIdUseCase listByIdUseCase;
    private final AddUserToRestaurantUseCase addUserToUseCase;
    private final RemoveUserFromRestaurantUseCase removeUserFromUseCase;
    private final ChangeOpeningHoursUseCase changeOpeningHoursUseCase;
    private final ListOpeningHoursUseCase listOpeningHoursUseCase;

    private final RestaurantWebMapper mapper;
    private final OpeningHoursWebMapper openingHoursMapper;
    private final AddressIntegrationPort addressIntegrationPort;

    // ======================
    // MUTATIONS
    // ======================

    @MutationMapping
    @PreAuthorize("hasAuthority('OWNER')")
    public RestaurantResponsePayload createRestaurant(
            @AuthenticationPrincipal String ownerId,
            @Argument @Valid RestaurantRequestInput input
    ) {

        RestaurantResultDTO result =
                createUseCase.execute(ownerId, mapper.toCreateDto(input));

        return mapper.toResponse(result);
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public RestaurantResponsePayload updateRestaurant(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId,
            @Argument UpdateRestaurantRequestPayload input
    ) {

        RestaurantResultDTO result =
                updateUseCase.execute(requesterId, restaurantId, mapper.toUpdateDto(input));

        return mapper.toResponse(result);
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public Boolean deleteRestaurant(
            @AuthenticationPrincipal String requesterId,
            @Argument String restaurantId
    ) {

        deleteUseCase.execute(requesterId, restaurantId);
        return true;
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public Boolean addUserToRestaurant(
            @Argument String restaurantId,
            @Argument String userId,
            @Argument RestaurantRole role,
            @AuthenticationPrincipal String requesterId
    ) {

        addUserToUseCase.execute(restaurantId, userId, role, requesterId);

        return true;
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public Boolean removeUserFromRestaurant(
            @Argument String restaurantId,
            @Argument String userId,
            @AuthenticationPrincipal String requesterId
    ) {

        removeUserFromUseCase.execute(
                restaurantId,
                userId,
                requesterId
        );

        return true;
    }

    @MutationMapping
    @PreAuthorize("!hasAuthority('CUSTOMER')")
    public Boolean changeOpeningHours(
            @Argument String restaurantId,
            @Argument DayOfWeek dayOfWeek,
            @Argument UpdateOpeningHoursInput input,
            @AuthenticationPrincipal String requesterId
    ) {

        UpdateOpeningHoursDTO dto = openingHoursMapper.toDto(input);

        changeOpeningHoursUseCase.execute(
                requesterId,
                restaurantId,
                dayOfWeek,
                dto
        );

        return true;
    }

    // ======================
    // QUERIES
    // ======================

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public RestaurantResponsePayload restaurantById(
            @Argument String restaurantId
    ) {

        return mapper.toResponse(
                listByIdUseCase.execute(restaurantId)
        );
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public PageResponsePayload listRestaurants(@Argument int page, @Argument int size) {
        PageResultDTO<RestaurantResultDTO> result =
                listAllUseCase.execute(new PageRequestDTO(page, size));

        return new PageResponsePayload(
                result.content().stream()
                        .map(mapper::toResponse)
                        .toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );
    }


    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<OpeningHoursResponsePayload> listOpeningHours(
            @Argument String restaurantId
    ) {

        return listOpeningHoursUseCase.execute(restaurantId)
                .stream()
                .map(openingHoursMapper::toResponse)
                .toList();
    }

    // ======================
    // FIELD RESOLVER (ADDRESS)
    // ======================

    @SchemaMapping(typeName = "Restaurant", field = "address")
    public AddressBaseResponse address(RestaurantResponsePayload dto) {

        var map = addressIntegrationPort.getByIds(Set.of(dto.addressBaseId()));

        AddressBaseDTO address = map.get(dto.addressBaseId());

        if (address == null) {
            throw new RuntimeException("Endereço não encontrado");
        }

        return new AddressBaseResponse(
                address.id(),
                address.cep(),
                address.street(),
                address.neighborhood(),
                address.city(),
                address.state(),
                address.country()
        );
    }
}