package br.com.foodhub.orderservice.infra.web.mapper;

import br.com.foodhub.orderservice.core.application.dto.order.CreateOrderRequestDTO;
import br.com.foodhub.orderservice.core.application.dto.order.OrderItemRequestDTO;
import br.com.foodhub.orderservice.core.application.dto.order.OrderResultDTO;
import br.com.foodhub.orderservice.infra.web.payload.order.CreateOrderInput;
import br.com.foodhub.orderservice.infra.web.payload.order.OrderItemResponsePayload;
import br.com.foodhub.orderservice.infra.web.payload.order.OrderResponsePayload;
import org.springframework.stereotype.Component;

@Component
public class OrderWebMapper {

    public CreateOrderRequestDTO toCreateDto(CreateOrderInput input) {
        return new CreateOrderRequestDTO(
                input.restaurantId(),
                input.menuId(),
                input.items().stream()
                        .map(i -> new OrderItemRequestDTO(
                                i.productId(),
                                i.quantity()
                        ))
                        .toList()
        );
    }

    public OrderResponsePayload toResponse(OrderResultDTO dto) {
        return new OrderResponsePayload(
                dto.id(),
                dto.userId(),
                dto.restaurantId(),
                dto.status(),
                dto.totalAmount(),
                dto.items().stream()
                        .map(i -> new OrderItemResponsePayload(
                                i.productId(),
                                i.name(),
                                i.quantity(),
                                i.price()
                        ))
                        .toList()
        );
    }
}
