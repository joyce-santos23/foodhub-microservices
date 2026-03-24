package br.com.foodhub.orderservice.infra.persistence.document.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDocument {

    private String productId;

    private String name;

    private Integer quantity;

    private BigDecimal price;
}