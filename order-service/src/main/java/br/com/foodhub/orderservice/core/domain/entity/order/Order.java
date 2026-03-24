package br.com.foodhub.orderservice.core.domain.entity.order;

import br.com.foodhub.orderservice.core.domain.entity.enums.OrderStatus;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {

    private String id;
    private String userId;
    private String restaurantId;

    private List<OrderItem> items;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private Order(
            String userId,
            String restaurantId,
            List<OrderItem> items
    ) {

        this.id = UUID.randomUUID().toString().replace("-", "");

        this.userId = require(userId, "Usuário");
        this.restaurantId = require(restaurantId, "Restaurante");

        if (items == null || items.isEmpty()) {
            throw new BusinessRuleViolationException(
                    "Pedido deve possuir itens"
            );
        }

        this.items = new ArrayList<>(items);

        recalculateTotal();

        this.status = OrderStatus.CREATED;

        this.createdAt = LocalDateTime.now();
    }

    private Order(
            String id,
            String userId,
            String restaurantId,
            List<OrderItem> items,
            BigDecimal totalAmount,
            OrderStatus status,
            LocalDateTime createdAt
    ) {

        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;

        this.items = items != null
                ? new ArrayList<>(items)
                : new ArrayList<>();

        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Order create(
            String userId,
            String restaurantId,
            List<OrderItem> items
    ) {

        return new Order(
                userId,
                restaurantId,
                items
        );
    }

    public static Order reconstitute(
            String id,
            String userId,
            String restaurantId,
            List<OrderItem> items,
            BigDecimal totalAmount,
            OrderStatus status,
            LocalDateTime createdAt
    ) {

        return new Order(
                id,
                userId,
                restaurantId,
                items,
                totalAmount,
                status,
                createdAt
        );
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    private void recalculateTotal() {

        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void markPaid() {

        if (status == OrderStatus.PAID) {
            throw new BusinessRuleViolationException(
                    "Pedido já está pago"
            );
        }

        this.status = OrderStatus.PAID;
    }

    public void markPendingPayment() {

        if (status == OrderStatus.PAID) {
            throw new BusinessRuleViolationException(
                    "Pedido já está pago"
            );
        }

        this.status = OrderStatus.PENDING_PAYMENT;
    }

    public void cancel() {

        if (status == OrderStatus.PAID) {
            throw new BusinessRuleViolationException(
                    "Pedido pago não pode ser cancelado"
            );
        }

        if (status == OrderStatus.CANCELED) {
            throw new BusinessRuleViolationException(
                    "Pedido já está cancelado"
            );
        }

        this.status = OrderStatus.CANCELED;
    }

    public void confirm() {
        this.status = OrderStatus.PENDING_PAYMENT;
    }

    public void validateOwner(String userId) {
        if (!this.userId.equals(userId)) {
            throw new BusinessRuleViolationException("Pedido não pertence ao usuário");
        }
    }

    private String require(String value, String field) {

        if (value == null || value.isBlank()) {
            throw new RequiredFieldException("O campo " + field + " é obrigatório");
        }

        return value;
    }

}