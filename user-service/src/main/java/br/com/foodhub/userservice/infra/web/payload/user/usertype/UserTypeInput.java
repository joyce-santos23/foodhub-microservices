package br.com.foodhub.userservice.infra.web.payload.user.usertype;

import jakarta.validation.constraints.NotBlank;

public record UserTypeInput(

        @NotBlank
        String name
) {
}
