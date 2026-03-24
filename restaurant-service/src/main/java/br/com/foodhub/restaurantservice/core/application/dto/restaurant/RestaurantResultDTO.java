package br.com.foodhub.restaurantservice.core.application.dto.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.openingHour.OpeningHoursDTO;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;

import java.util.List;

public record RestaurantResultDTO(
        String restaurantId,
        String businessName,
        String cnpj,
        String cuisineType,
        String ownerId,
        String addressBaseId,
        String numberStreet,
        String complement,
        List<OpeningHoursDTO> openingHours
) {

    public static RestaurantResultDTO from(Restaurant restaurant) {

        List<OpeningHoursDTO> openingHoursDTOs =
                restaurant.getOpeningHours()
                        .stream()
                        .map(OpeningHoursDTO::from)
                        .toList();

        return new RestaurantResultDTO(
                restaurant.getId(),
                restaurant.getBusinessName(),
                restaurant.getCnpj(),
                restaurant.getCuisineType(),
                restaurant.getOwnerId(),
                restaurant.getAddressBaseId(),
                restaurant.getNumberStreet(),
                restaurant.getComplement(),
                openingHoursDTOs
        );
    }
}
