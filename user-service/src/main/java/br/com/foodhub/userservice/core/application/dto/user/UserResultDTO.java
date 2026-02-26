package br.com.foodhub.userservice.core.application.dto.user;

import br.com.foodhub.userservice.core.domain.entity.user.User;

import java.util.List;

public record UserResultDTO(
        String id,
        String name,
        String email,
        String phone,
        String cpf,
        String userTypeId
) {
    public static UserResultDTO from(User user) {

        return new UserResultDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCpf(),
                user.getUserType().getId()
        );
    }
}

