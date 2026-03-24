package br.com.foodhub.restaurantservice.infra.initializer;

import br.com.foodhub.restaurantservice.infra.persistence.document.menu.MenuDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.menu.MenuItemDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.restaurant.RestaurantDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.restaurant.RestaurantUserDocument;
import br.com.foodhub.restaurantservice.infra.persistence.repository.restaurant.RestaurantMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RestaurantMongoRepository restaurantRepository;

    @Override
    public void run(String... args) {

        String ownerId = "239a64c2e8df4ba2a9e068d084969736";
        String restaurantId = "69c2ee4ac1df24c267db27a3";

        // =========================
        // RESTAURANT
        // =========================
        if (!restaurantRepository.existsByCnpj("78965412358569712")) {

            RestaurantDocument restaurant = new RestaurantDocument();

            restaurant.setId(restaurantId);
            restaurant.setBusinessName("FoodHub Restaurante");
            restaurant.setCnpj("78965412358569712");
            restaurant.setCuisineType("Brasileira");
            restaurant.setAddressBaseId("8e5437f31e19403c8cd9fc176d62e9d1");
            restaurant.setNumberStreet("30");

            restaurant.setUsers(List.of(
                    new RestaurantUserDocument(ownerId, "OWNER")
            ));

            restaurantRepository.save(restaurant);
        }

        // =========================
        // MENU + ITEMS
        // =========================
        RestaurantDocument restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow();

        if (restaurant.getMenus() == null || restaurant.getMenus().isEmpty()) {

            MenuItemDocument item1 = new MenuItemDocument(
                    "9fb946683a54471093f6a259fa9e75eb",
                    "HAMBURGUER DUPLO",
                    null,
                    new BigDecimal("30.00"),
                    false,
                    "url"
            );

            MenuItemDocument item2 = new MenuItemDocument(
                    "56bca64d805d46ed8a1fc1ab0af66bf7",
                    "HAMBURGUER SIMPLES",
                    null,
                    new BigDecimal("25.90"),
                    false,
                    "url"
            );

            MenuItemDocument item3 = new MenuItemDocument(
                    "84019ee4896a4a9fb2d3f5f34a75c31e",
                    "COCA COLA LATA",
                    null,
                    new BigDecimal("7.50"),
                    false,
                    "url"
            );

            MenuDocument menu = new MenuDocument(
                    "6a66705182194c239b84639123c6f3a1",
                    "Almoço",
                    List.of(item1, item2, item3)
            );

            restaurant.setMenus(List.of(menu));

            restaurantRepository.save(restaurant);
        }
    }
}