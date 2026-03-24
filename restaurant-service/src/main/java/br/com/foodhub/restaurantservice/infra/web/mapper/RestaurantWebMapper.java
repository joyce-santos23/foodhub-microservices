package br.com.foodhub.restaurantservice.infra.web.mapper;

import br.com.foodhub.restaurantservice.core.application.dto.AddressBaseDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.UpdateRestaurantDTO;
import br.com.foodhub.restaurantservice.infra.web.payload.address.AddressBaseResponse;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.RestaurantRequestInput;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.RestaurantResponsePayload;
import br.com.foodhub.restaurantservice.infra.web.payload.restaurant.UpdateRestaurantRequestPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantWebMapper {

    private final OpeningHoursWebMapper openingHoursMapper;

    public RestaurantRequestDTO toCreateDto(RestaurantRequestInput input) {
        return new RestaurantRequestDTO(
                input.businessName(),
                input.cnpj(),
                input.cuisineType(),
                input.cep(),
                input.numberStreet(),
                input.complement()
        );
    }

    public UpdateRestaurantDTO toUpdateDto(UpdateRestaurantRequestPayload input) {
        return new UpdateRestaurantDTO(
                input.businessName(),
                input.cuisineType(),
                input.numberStreet(),
                input.complement()
        );
    }

    public RestaurantResponsePayload toResponse(RestaurantResultDTO dto) {
        return new RestaurantResponsePayload(
                dto.restaurantId(),
                dto.businessName(),
                dto.cnpj(),
                dto.cuisineType(),
                dto.ownerId(),
                dto.numberStreet(),
                dto.complement(),
                dto.addressBaseId(),
                dto.openingHours().stream()
                        .map(openingHoursMapper::toResponse)
                        .toList()
        );
    }

    private AddressBaseResponse toAddressResponse(AddressBaseDTO dto) {
        return new AddressBaseResponse(
                dto.id(),
                dto.cep(),
                dto.street(),
                dto.neighborhood(),
                dto.city(),
                dto.state(),
                dto.country()
        );
    }

}