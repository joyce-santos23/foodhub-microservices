package br.com.foodhub.userservice.core.application.dto.user;

import br.com.foodhub.userservice.core.domain.entity.user.User;

public record UserAuthDTO(
        String id,
        String email,
        String password,
        String role
) {
    public static UserAuthDTO from(User user) {

        return new UserAuthDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUserType().getName()
        );
    }
}