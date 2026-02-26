package br.com.foodhub.userservice.core.application.dto.user.usertype;

import br.com.foodhub.userservice.core.domain.entity.user.UserType;

public record UserTypeResultDTO(
        String id,
        String name
) {
    public static UserTypeResultDTO from(UserType userType) {
        return new UserTypeResultDTO(
                userType.getId(),
                userType.getName()
        );
    }
}
