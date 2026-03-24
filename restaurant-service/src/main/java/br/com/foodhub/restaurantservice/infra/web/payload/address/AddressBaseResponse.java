package br.com.foodhub.restaurantservice.infra.web.payload.address;

public record AddressBaseResponse(
        String id,
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        String country
) {
}
