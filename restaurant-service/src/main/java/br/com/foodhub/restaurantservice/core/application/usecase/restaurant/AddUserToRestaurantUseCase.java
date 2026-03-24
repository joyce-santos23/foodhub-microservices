package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantRole;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantRole.CUSTOMER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddUserToRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public void execute(String restaurantId, String userId, RestaurantRole role, String requesterId) {
        log.info("Iniciando processo AddUserToRestaurantUseCase...");

        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado " + restaurantId));

        restaurant.ensureUserCanManage(requesterId);

        if (role == CUSTOMER) {
            throw new BusinessRuleViolationException("Cliente não pode ser associado");
        }

        restaurant.addUser(userId, role);

        restaurantGateway.save(restaurant);

        log.info("Usuário {} adicionado ao restaurante {} com sucesso!", userId, restaurantId);
    }
}
