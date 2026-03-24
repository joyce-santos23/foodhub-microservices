package br.com.foodhub.restaurantservice.core.application.dto;

public record AddressBaseDTO(
        String id,
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        String country
) {
}
