package br.com.foodhub.orderservice.infra.persistence.mapper.order;

import br.com.foodhub.orderservice.core.domain.entity.enums.OrderStatus;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.entity.order.OrderItem;
import br.com.foodhub.orderservice.infra.persistence.document.order.OrderDocument;
import br.com.foodhub.orderservice.infra.persistence.document.order.OrderItemDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderDocument toDocument(Order order) {

        OrderDocument doc = new OrderDocument();

        doc.setId(order.getId());
        doc.setUserId(order.getUserId());
        doc.setRestaurantId(order.getRestaurantId());
        doc.setTotalAmount(order.getTotalAmount());
        doc.setStatus(order.getStatus().name());
        doc.setCreatedAt(order.getCreatedAt());

        doc.setItems(
                order.getItems()
                        .stream()
                        .map(item ->
                                new OrderItemDocument(
                                        item.getProductId(),
                                        item.getName(),
                                        item.getQuantity(),
                                        item.getPrice()
                                )
                        )
                        .toList()
        );

        return doc;
    }

    public Order toDomain(OrderDocument doc) {

        List<OrderItem> items = doc.getItems()
                .stream()
                .map(item ->
                        new OrderItem(
                                item.getProductId(),
                                item.getName(),
                                item.getQuantity(),
                                item.getPrice()
                        )
                )
                .toList();

        return Order.reconstitute(
                doc.getId(),
                doc.getUserId(),
                doc.getRestaurantId(),
                items,
                doc.getTotalAmount(),
                OrderStatus.valueOf(doc.getStatus()),
                doc.getCreatedAt()
        );
    }
}