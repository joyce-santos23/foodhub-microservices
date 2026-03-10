package br.com.foodhub.restaurantservice.infra.persistence.document.restaurant;

import br.com.foodhub.restaurantservice.infra.persistence.document.menu.MenuDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurants")
public class RestaurantDocument {

    @Id
    private String id;

    private String businessName;
    private String cnpj;
    private String cuisineType;

    private String addressBaseId;
    private String numberStreet;
    private String complement;

    private List<OpeningHoursDocument> openingHours;
    private List<MenuDocument> menus;
    private List<RestaurantUserDocument> users;
}

