package br.com.foodhub.orderservice.infra.web.graphql;

import br.com.foodhub.orderservice.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.orderservice.core.application.usecase.order.*;
import br.com.foodhub.orderservice.infra.web.mapper.OrderWebMapper;
import br.com.foodhub.orderservice.infra.web.payload.order.CreateOrderInput;
import br.com.foodhub.orderservice.infra.web.payload.order.OrderResponsePayload;
import br.com.foodhub.orderservice.infra.web.payload.pagination.PageResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("isAuthenticated()")
public class OrderResolver {

    private final CreateOrderUseCase createUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final GetOrderByIdUseCase getByIdUseCase;
    private final ListOrdersByUserUseCase listByUserUseCase;
    private final CancelOrderUseCase cancelUseCase;

    private final OrderWebMapper mapper;

    // ======================
    // MUTATIONS
    // ======================

    @MutationMapping
    public OrderResponsePayload createOrder(
            @AuthenticationPrincipal String userId,
            @Argument @Valid CreateOrderInput input
    ) {
        var result = createUseCase.execute(userId, mapper.toCreateDto(input));
        return mapper.toResponse(result);
    }

    @MutationMapping
    public OrderResponsePayload confirmOrder(
            @AuthenticationPrincipal String requesterId,
            @Argument String orderId
    ) {
        var result = confirmOrderUseCase.execute(orderId, requesterId);
        return mapper.toResponse(result);
    }

    @MutationMapping
    public Boolean cancelOrder( @AuthenticationPrincipal String requesterId, @Argument String orderId) {
        cancelUseCase.execute(orderId, requesterId);
        return true;
    }

    // ======================
    // QUERIES
    // ======================

    @QueryMapping
    public OrderResponsePayload orderById(@AuthenticationPrincipal String requesterId, @Argument String orderId) {
        return mapper.toResponse(getByIdUseCase.execute(orderId, requesterId));
    }

    @QueryMapping
    public PageResponsePayload listOrdersByUser(
            @AuthenticationPrincipal String requesterId,
            @Argument String userId,
            @Argument int page,
            @Argument int size
    ) {
        var result = listByUserUseCase.execute(
                requesterId,
                userId,
                new PageRequestDTO(page, size)
        );

        return new PageResponsePayload(
                result.content().stream()
                        .map(mapper::toResponse)
                        .toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );
    }
}
