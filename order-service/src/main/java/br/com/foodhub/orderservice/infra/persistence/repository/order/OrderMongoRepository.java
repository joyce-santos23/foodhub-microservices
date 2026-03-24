package br.com.foodhub.orderservice.infra.persistence.repository.order;

import br.com.foodhub.orderservice.infra.persistence.document.order.OrderDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderMongoRepository
        extends MongoRepository<OrderDocument, String> {

    Page<OrderDocument> findByUserId(String userId, Pageable pageable);
}