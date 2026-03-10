package br.com.foodhub.restaurantservice.infra.persistence.document.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantUserDocument {

    private String userId;
    private String role;
}