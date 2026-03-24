package br.com.foodhub.orderservice.infra.persistence.gateway.order;

import br.com.foodhub.orderservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.infra.persistence.mapper.order.OrderMapper;
import br.com.foodhub.orderservice.infra.persistence.repository.order.OrderMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderGatewayAdapter implements OrderGateway {

    private final OrderMongoRepository repository;

    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {

        var document = mapper.toDocument(order);

        var saved = repository.save(document);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(String orderId) {

        return repository.findById(orderId)
                .map(mapper::toDomain);
    }

    @Override
    public PageResultDTO<Order> findByUserId(
            String userId,
            int page,
            int size
    ) {

        var pageable = PageRequest.of(page, size);

        var pageResult = repository.findByUserId(userId, pageable);

        var content = pageResult.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PageResultDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }
}
