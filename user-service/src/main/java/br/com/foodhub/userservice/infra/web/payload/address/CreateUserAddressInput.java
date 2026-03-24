package br.com.foodhub.userservice.infra.web.payload.address;

import jakarta.validation.constraints.NotBlank;

public record CreateUserAddressInput(
        @NotBlank
        String cep,

        @NotBlank
        String number,
        String complement,

        boolean primary
) {}
