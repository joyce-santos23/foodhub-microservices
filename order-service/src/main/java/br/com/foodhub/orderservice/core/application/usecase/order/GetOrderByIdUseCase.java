package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.dto.order.OrderResultDTO;
import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.application.port.restaurant.RestaurantIntegrationPort;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCase {

    private final OrderGateway gateway;
    private final RestaurantIntegrationPort restaurantPort;

    public OrderResultDTO execute(String orderId, String userId) {
        log.info("Buscando pedido {}", orderId);

        Order order = gateway.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado")
                );

        validateAccess(order, userId);

        return OrderResultDTO.from(order);
    }

    private void validateAccess(Order order, String userId) {

        boolean isOwner = order.getUserId().equals(userId);

        boolean isRestaurantUser =
                restaurantPort.isUserLinkedToRestaurant(
                        userId,
                        order.getRestaurantId()
                );

        if (!isOwner && !isRestaurantUser) {
            throw new UnauthorizedException("Acesso negado ao pedido");
        }
    }
}