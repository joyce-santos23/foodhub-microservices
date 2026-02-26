package br.com.foodhub.userservice.core.application.dto.address;

public record UpdateUserAddressDTO(
        String number,
        String complement,
        boolean primary
) {
}
