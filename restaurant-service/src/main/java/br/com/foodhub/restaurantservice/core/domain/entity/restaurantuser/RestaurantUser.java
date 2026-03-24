package br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser;

import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

@Getter
public class RestaurantUser {

    private final String userId;
    private RestaurantRole role;

    public RestaurantUser(String userId, RestaurantRole role) {
        if (userId == null || userId.isBlank()) {
            throw new RequiredFieldException("Usuário é obrigatório");
        }
        if (role == null) {
            throw new RequiredFieldException("Papel no restaurante é obrigatório");
        }

        this.userId = userId;
        this.role = role;
    }
}
