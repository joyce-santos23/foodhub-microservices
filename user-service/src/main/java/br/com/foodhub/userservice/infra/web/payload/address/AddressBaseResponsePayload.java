package br.com.foodhub.userservice.infra.web.payload.address;


public record AddressBaseResponsePayload(
        String id,
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        String country
) {}

