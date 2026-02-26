package br.com.foodhub.userservice.core.application.dto.user;

public record UpdateUserDTO (
        String name,
        String email,
        String phone
) {
}
