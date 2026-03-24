package br.com.foodhub.userservice.infra.web.payload.address;


public record UserAddressResponsePayload(

        String id,
        boolean primary,
        String number,
        String complement,
        AddressBaseResponsePayload address
) {}
