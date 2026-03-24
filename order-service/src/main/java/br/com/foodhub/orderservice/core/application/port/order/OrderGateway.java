package br.com.foodhub.orderservice.core.application.port.order;

import br.com.foodhub.orderservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {

    Order save(Order order);

    Optional<Order> findById(String orderId);

    PageResultDTO<Order> findByUserId(String userId, int page, int size);
}