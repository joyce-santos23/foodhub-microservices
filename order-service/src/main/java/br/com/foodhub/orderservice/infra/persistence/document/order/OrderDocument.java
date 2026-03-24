package br.com.foodhub.orderservice.infra.persistence.document.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;

    private String userId;

    private String restaurantId;

    private List<OrderItemDocument> items;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime createdAt;
}