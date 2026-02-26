package br.com.foodhub.userservice.core.application.dto.user;

public record UserRequestDTO(
        String name,
        String email,
        String phone,
        String cpf,
        String password,
        String userTypeId
) {
}
