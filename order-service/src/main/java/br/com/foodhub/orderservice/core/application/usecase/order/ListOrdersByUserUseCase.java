package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.dto.order.OrderResultDTO;
import br.com.foodhub.orderservice.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.orderservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListOrdersByUserUseCase {

    private final OrderGateway gateway;

    public PageResultDTO<OrderResultDTO> execute(
            String requesterId,
            String userId,
            PageRequestDTO dto
    ) {

        log.info("Buscando pedidos para o usuário {}", userId);

        boolean isOwner = requesterId.equals(userId);

        PageResultDTO<Order> page =
                gateway.findByUserId(
                        userId,
                        dto.page(),
                        dto.size()
                );

        return new PageResultDTO<>(
                page.content().stream()
                        .map(OrderResultDTO::from)
                        .toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );
    }
}