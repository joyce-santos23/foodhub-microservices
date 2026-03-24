package br.com.foodhub.orderservice.infra.integration.grpc;

import br.com.foodhub.contracts.restaurant.*;
import br.com.foodhub.orderservice.core.application.dto.restaurant.MenuItemDTO;
import br.com.foodhub.orderservice.core.application.port.restaurant.RestaurantIntegrationPort;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantGrpcClient implements RestaurantIntegrationPort {

    private final RestaurantServiceGrpc.RestaurantServiceBlockingStub stub;

    @Override
    public void validateRestaurant(String restaurantId) {

        ValidateRestaurantRequest request =
                ValidateRestaurantRequest.newBuilder()
                        .setRestaurantId(restaurantId)
                        .build();

        try {

            ValidateRestaurantResponse response = stub.validateRestaurant(request);

            if (!response.getExists()) {
                throw new RuntimeException(
                        "Restaurante não encontrado: " + restaurantId
                );
            }

        } catch (StatusRuntimeException ex) {

            throw new RuntimeException(
                    "Erro ao validar restaurante no restaurant-service",
                    ex
            );
        }
    }

    @Override
    public List<MenuItemDTO> findMenuItems(
            String restaurantId,
            String menuId,
            List<String> itemIds
    ) {

        MenuItemsRequest request = MenuItemsRequest.newBuilder()
                .setRestaurantId(restaurantId)
                .setMenuId(menuId)
                .addAllItemIds(itemIds)
                .build();

        try {

            MenuItemsResponse response = stub.findMenuItems(request);

            return response.getItemsList()
                    .stream()
                    .map(this::map)
                    .toList();

        } catch (StatusRuntimeException ex) {

            throw new RuntimeException(
                    "Erro ao consultar itens do menu no restaurant-service",
                    ex
            );
        }
    }

    @Override
    public boolean isUserLinkedToRestaurant(String userId, String restaurantId) {

        try {
            UserRestaurantRequest request = UserRestaurantRequest.newBuilder()
                    .setUserId(userId)
                    .setRestaurantId(restaurantId)
                    .build();

            UserRestaurantResponse response =
                    stub.isUserLinkedToRestaurant(request);

            return response.getLinked();

        } catch (Exception e) {
            log.error("Erro ao validar usuário no restaurante", e);
            return false;
        }
    }

    private MenuItemDTO map(MenuItem item) {

        return new MenuItemDTO(
                item.getId(),
                item.getName(),
                BigDecimal.valueOf(item.getPrice())
        );
    }
}