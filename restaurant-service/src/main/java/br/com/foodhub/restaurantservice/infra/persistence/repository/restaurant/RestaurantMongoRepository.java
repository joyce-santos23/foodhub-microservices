package br.com.foodhub.restaurantservice.infra.persistence.repository.restaurant;

import br.com.foodhub.restaurantservice.infra.persistence.document.restaurant.RestaurantDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantMongoRepository extends MongoRepository<RestaurantDocument, String> {
    boolean existsByCnpj(String cnpj);
}

