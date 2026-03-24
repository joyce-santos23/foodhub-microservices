package br.com.foodhub.userservice.infra.web.payload.address;


public record UpdateUserAddressRequestPayload(

        String number,
        String complement,
        Boolean primary
) {
}
