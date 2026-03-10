package br.com.foodhub.restaurantservice.infra.persistence.document.menu;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDocument {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean inRestaurantOnly;
    private String photograph;
}

