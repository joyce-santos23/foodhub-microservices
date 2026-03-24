package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.address.AddressIntegrationPort;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.application.port.user.UserIntegrationPort;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserIntegrationPort userIntegrationPort;
    private final AddressIntegrationPort addressIntegrationPort;

    public RestaurantResultDTO execute(String ownerId, RestaurantRequestDTO dto) {
        log.info("Criando restaurante");

        if (!userIntegrationPort.canCreateRestaurant(ownerId)) {
            throw new BusinessRuleViolationException(
                    "Usuário não pode criar restaurante"
            );
        }

        String normalizedCnpj = dto.cnpj().replaceAll("\\D", "");

        if (restaurantGateway.existsByCnpj(normalizedCnpj)) {
            throw new BusinessRuleViolationException(
                    "Já existe restaurante com este CNPJ"
            );
        }

        String addressBaseId =
                addressIntegrationPort.findOrCreateAddress(dto.cep());

        Restaurant restaurant = new Restaurant(
                dto.businessName(),
                normalizedCnpj,
                dto.cuisineType(),
                addressBaseId,
                dto.numberStreet(),
                dto.complement(),
                ownerId
        );

        Restaurant saved = restaurantGateway.save(restaurant);

        return RestaurantResultDTO.from(saved);
    }
}
