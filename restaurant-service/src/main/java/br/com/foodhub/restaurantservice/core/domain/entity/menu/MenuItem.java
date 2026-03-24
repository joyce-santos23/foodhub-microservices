package br.com.foodhub.restaurantservice.core.domain.entity.menu;

import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.RequiredFieldException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuItem {
    private String id;
    @EqualsAndHashCode.Include
    private String name;
    private String description;
    private BigDecimal price;
    private boolean inRestaurantOnly;
    private String photograph;

    public MenuItem(
            String name,
            String description,
            BigDecimal price,
            boolean inRestaurantOnly,
            String photograph
    ) {
        this.id = UUID.randomUUID().toString().replace("-","");
        this.name = require(name, "Nome do item").trim().toUpperCase();
        this.description = description;
        this.price = validatePrice(require(price, "Preço"));
        this.inRestaurantOnly = inRestaurantOnly;
        this.photograph = normalizeOptional(photograph);
    }

    private MenuItem(
            String id,
            String name,
            String description,
            BigDecimal price,
            boolean inRestaurantOnly,
            String photograph
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inRestaurantOnly = inRestaurantOnly;
        this.photograph = photograph;
    }

    public static MenuItem reconstitute(
            String id,
            String name,
            String description,
            BigDecimal price,
            boolean inRestaurantOnly,
            String photograph
    ) {
        return new MenuItem(
                id,
                name,
                description,
                price,
                inRestaurantOnly,
                photograph
        );
    }

    /* =========================
       Validações de domínio
       ========================= */
    public void update(
            String name,
            String description,
            BigDecimal price,
            boolean inRestaurantOnly,
            String photograph
    ) {
        this.name = require(name, "Nome do item").trim().toUpperCase();
        this.description = description;
        this.price = validatePrice(require(price, "Preço"));
        this.inRestaurantOnly = inRestaurantOnly;
        this.photograph = normalizeOptional(photograph);
    }

    private BigDecimal validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleViolationException(
                    "O preço do item não pode ser negativo"
            );
        }
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    private String normalizeOptional(String value) {
        if (value == null) return null;
        if (value.isBlank()) {
            throw new RequiredFieldException("Fotografia não pode ser vazio");
        }
        return value;
    }

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

    private <T> T require(T value, String field) {
        if (value == null) {
            throw new RequiredFieldException(field);
        }
        return value;
    }
}
