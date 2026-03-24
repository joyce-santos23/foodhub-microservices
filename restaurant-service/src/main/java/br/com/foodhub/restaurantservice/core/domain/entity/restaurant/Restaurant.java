package br.com.foodhub.restaurantservice.core.domain.entity.restaurant;

import br.com.foodhub.restaurantservice.core.domain.entity.menu.Menu;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantRole;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantUser;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.restaurantservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
public class Restaurant {

    private String id;
    private String businessName;
    private String cnpj;
    private String cuisineType;
    private String addressBaseId;
    private String numberStreet;
    private String complement;

    private final List<OpeningHours> openingHours;
    private final List<Menu> menus;
    private final List<RestaurantUser> users;

    public Restaurant(
            String businessName,
            String cnpj,
            String cuisineType,
            String addressBaseId,
            String numberStreet,
            String complement,
            String ownerUserId
    ) {
        this.id = UUID.randomUUID().toString().replace("-","");
        this.businessName = require(businessName, "Nome do restaurante");
        this.cnpj = normalizeCnpj(require(cnpj, "CNPJ"));
        this.cuisineType = require(cuisineType, "Tipo de cozinha");
        this.addressBaseId = require(addressBaseId, "Endereço");
        this.numberStreet = require(numberStreet, "Número");
        this.complement = complement;

        this.openingHours = new ArrayList<>();
        this.menus = new ArrayList<>();
        this.users = new ArrayList<>();

        // Regra: Restaurante nasce com um dono
        this.users.add(new RestaurantUser(ownerUserId, RestaurantRole.OWNER));
    }

    private Restaurant(
            String id,
            String businessName,
            String cnpj,
            String cuisineType,
            String addressBaseId,
            String numberStreet,
            String complement,
            List<OpeningHours> openingHours,
            List<Menu> menus,
            List<RestaurantUser> users
    ) {
        this.id = id;
        this.businessName = businessName;
        this.cnpj = cnpj;
        this.cuisineType = cuisineType;
        this.addressBaseId = addressBaseId;
        this.numberStreet = numberStreet;
        this.complement = complement;
        this.openingHours = openingHours != null ? new ArrayList<>(openingHours) : new ArrayList<>();
        this.menus = menus != null ? new ArrayList<>(menus) : new ArrayList<>();
        this.users = users != null ? new ArrayList<>(users) : new ArrayList<>();
    }
    /* =========================
       Reconstituição
       ========================= */

    public static Restaurant reconstitute(
            String id,
            String businessName,
            String cnpj,
            String cuisineType,
            String addressBaseId,
            String numberStreet,
            String complement,
            List<OpeningHours> openingHours,
            List<Menu> menus,
            List<RestaurantUser> users
    ) {
        return new Restaurant(
                id,
                businessName,
                cnpj,
                cuisineType,
                addressBaseId,
                numberStreet,
                complement,
                openingHours,
                menus,
                users
        );
    }

    /* =========================
       Associação de usuários
       ========================= */

    public void addUser(String userId, RestaurantRole role) {

        require(userId, "Usuário");
        require(role, "Papel");

        if (role == RestaurantRole.OWNER && hasOwner()) {
            throw new BusinessRuleViolationException("Restaurante já possui dono");
        }

        boolean alreadyAssociated = users.stream()
                .anyMatch(u -> u.getUserId().equals(userId));

        if (alreadyAssociated) {
            throw new BusinessRuleViolationException("Usuário já está associado ao restaurante");
        }

        users.add(new RestaurantUser(userId, role));
    }

    public void removeUser(String userId) {

        RestaurantUser user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não associado ao restaurante")
                );

        if (user.getRole() == RestaurantRole.OWNER) {
            throw new BusinessRuleViolationException("Não é possível remover o dono do restaurante");
        }

        users.remove(user);
    }


    public String getOwnerId() {
        return users.stream()
                .filter(user -> user.getRole() == RestaurantRole.OWNER)
                .findFirst()
                .map(RestaurantUser::getUserId)
                .orElseThrow(() ->
                        new IllegalStateException("Restaurante sem dono")
                );
    }

    private boolean hasOwner() {
        return users.stream()
                .anyMatch(u -> u.getRole() == RestaurantRole.OWNER);
    }

    public boolean hasUser(String userId) {
        return users.stream()
                .anyMatch(u -> u.getUserId().equals(userId));
    }

    public void ensureUserCanManage(String userId) {

        System.out.println("Validando userId: " + userId);
        System.out.println("Users: " + users);

        if (!hasUser(userId)) {
            throw new BusinessRuleViolationException(
                    "Usuário não pertence ao restaurante"
            );
        }
    }

    public void updateBasicInfo(
            String businessName,
            String cuisineType,
            String numberStreet,
            String complement
    ) {

        if (businessName != null && !businessName.isBlank()) {
            this.businessName = require(businessName, "Nome do restaurante");
        }

        if (cuisineType != null && !cuisineType.isBlank()) {
            this.cuisineType = require(cuisineType, "Tipo de cozinha");
        }

        if (numberStreet != null && !numberStreet.isBlank()) {
            this.numberStreet = require(numberStreet, "Número");
        }

        if (complement != null && !complement.isBlank()) {
            this.complement = complement;
        }
    }

    public void addMenu(Menu menu) {
        require(menu, "Menu");

        boolean exists = menus.stream()
                .anyMatch(m -> m.getName().equalsIgnoreCase(menu.getName()));

        if (exists) {
            throw new BusinessRuleViolationException("Já existe um menu com esse nome");
        }

        this.menus.add(menu);
    }

    public void removeMenu(String menuId) {

        boolean removed = menus.removeIf(menu -> menu.getId().equals(menuId));

        if (!removed) {
            throw new BusinessRuleViolationException(
                    "Menu não pertence ao restaurante"
            );
        }
    }

    public List<Menu> getMenus() {
        return List.copyOf(menus);
    }

    public Menu getMenuById(String menuId) {
        return menus.stream()
                .filter(menu -> menu.getId().equals(menuId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu não pertence ao restaurante")
                );
    }
    /* =========================
       Opening Hours
       ========================= */

    public void changeOpeningHours(
            DayOfWeek dayOfWeek,
            LocalTime openTime,
            LocalTime closeTime,
            boolean closed
    ) {
        openingHours.removeIf(oh -> oh.getDayOfWeek().equals(dayOfWeek));
        openingHours.add(new OpeningHours(dayOfWeek, openTime, closeTime, closed));
    }

    /* =========================
       Utils
       ========================= */

    private String normalizeCnpj(String cnpj) {
        return cnpj.replaceAll("\\D", "");
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