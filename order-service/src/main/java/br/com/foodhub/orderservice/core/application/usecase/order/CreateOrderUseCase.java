package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.dto.order.CreateOrderRequestDTO;
import br.com.foodhub.orderservice.core.application.dto.order.OrderItemRequestDTO;
import br.com.foodhub.orderservice.core.application.dto.order.OrderResultDTO;
import br.com.foodhub.orderservice.core.application.dto.restaurant.MenuItemDTO;
import br.com.foodhub.orderservice.core.application.port.event.OrderEventPublisher;
import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.application.port.payment.PaymentIntegrationPort;
import br.com.foodhub.orderservice.core.application.port.restaurant.RestaurantIntegrationPort;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.entity.order.OrderItem;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.PaymentFailedException;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway gateway;
    private final RestaurantIntegrationPort restaurantPort;

    public OrderResultDTO execute(String userId, CreateOrderRequestDTO dto) {

        restaurantPort.validateRestaurant(dto.restaurantId());

        List<String> itemIds = dto.items().stream()
                .map(OrderItemRequestDTO::productId)
                .toList();

        List<MenuItemDTO> menuItems =
                restaurantPort.findMenuItems(
                        dto.restaurantId(),
                        dto.menuId(),
                        itemIds
                );

        Map<String, MenuItemDTO> menuMap = menuItems.stream()
                .collect(Collectors.toMap(MenuItemDTO::id, m -> m));

        List<OrderItem> items = dto.items().stream()
                .map(req -> {

                    MenuItemDTO menuItem = menuMap.get(req.productId());

                    if (menuItem == null) {
                        throw new ResourceNotFoundException(
                                "Item não encontrado no menu: " + req.productId()
                        );
                    }

                    return new OrderItem(
                            menuItem.id(),
                            menuItem.name(),
                            req.quantity(),
                            menuItem.price()
                    );

                }).toList();

        Order order = Order.create(
                userId,
                dto.restaurantId(),
                items
        );

        gateway.save(order);

        return OrderResultDTO.from(order);
    }
}