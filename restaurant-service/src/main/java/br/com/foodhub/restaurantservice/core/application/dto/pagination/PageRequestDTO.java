package br.com.foodhub.restaurantservice.core.application.dto.pagination;

public record PageRequestDTO(
        int page,
        int size
) {
}
