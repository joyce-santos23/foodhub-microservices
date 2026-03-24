package br.com.foodhub.orderservice.core.domain.entity.order;

import br.com.foodhub.orderservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.RequiredFieldException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItem {

    private final String productId;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

    public OrderItem(
            String productId,
            String name,
            int quantity,
            BigDecimal price
    ) {

        this.productId = require(productId, "Produto");
        this.name = require(name, "Nome do item");

        if (quantity <= 0) {
            throw new BusinessRuleViolationException(
                    "Quantidade deve ser maior que zero"
            );
        }

        this.quantity = quantity;

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolationException(
                    "Preço inválido"
            );
        }

        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException("O campo " + field + " é obrigatório.");
        }
        return value;
    }
}
