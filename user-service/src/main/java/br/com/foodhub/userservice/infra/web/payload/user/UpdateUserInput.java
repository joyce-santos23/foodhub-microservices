package br.com.foodhub.userservice.infra.web.payload.user;

import jakarta.validation.constraints.Email;

public record UpdateUserInput(
        String name,
        @Email
        String email,
        String phone
) {}
