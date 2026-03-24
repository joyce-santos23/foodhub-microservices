package br.com.foodhub.orderservice.core.application.dto.pagination;

public record PageRequestDTO(
        int page,
        int size
) {
}
