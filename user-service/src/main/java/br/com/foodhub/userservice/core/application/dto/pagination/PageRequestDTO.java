package br.com.foodhub.userservice.core.application.dto.pagination;

public record PageRequestDTO(
        int page,
        int size
) {
}
