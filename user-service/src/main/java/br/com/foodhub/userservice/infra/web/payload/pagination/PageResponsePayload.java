package br.com.foodhub.userservice.infra.web.payload.pagination;

import java.util.List;

public record PageResponsePayload<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
