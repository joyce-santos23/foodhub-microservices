package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantRole;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveUserFromRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public void execute(String restaurantId, String userId, String requesterId) {
        log.info("Iniciando processo de RemoveUserFromRestaurantUseCase");

        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + restaurantId));

        restaurant.ensureUserCanManage(requesterId);

        restaurant.removeUser(userId);

        restaurantGateway.save(restaurant);

        log.info("Processo de remoção do usuário: {} finalizado!", userId);
    }
}

