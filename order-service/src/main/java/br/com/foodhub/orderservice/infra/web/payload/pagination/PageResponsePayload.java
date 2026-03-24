package br.com.foodhub.orderservice.infra.web.payload.pagination;

import br.com.foodhub.orderservice.infra.web.payload.order.OrderResponsePayload;

import java.util.List;

public record PageResponsePayload(
        List<OrderResponsePayload> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
