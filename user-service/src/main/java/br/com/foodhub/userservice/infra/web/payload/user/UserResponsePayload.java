package br.com.foodhub.userservice.infra.web.payload.user;

public record UserResponsePayload(
        String id,
        String name,
        String email,
        String phone,
        String cpf,
        String userTypeId
) {
}
