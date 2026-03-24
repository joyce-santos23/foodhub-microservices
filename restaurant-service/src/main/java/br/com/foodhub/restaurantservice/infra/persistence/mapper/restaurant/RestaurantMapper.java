package br.com.foodhub.restaurantservice.infra.persistence.mapper.restaurant;

import br.com.foodhub.restaurantservice.core.domain.entity.menu.Menu;
import br.com.foodhub.restaurantservice.core.domain.entity.menu.MenuItem;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.OpeningHours;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantRole;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurantuser.RestaurantUser;
import br.com.foodhub.restaurantservice.infra.persistence.document.menu.MenuDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.menu.MenuItemDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.restaurant.OpeningHoursDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.restaurant.RestaurantDocument;
import br.com.foodhub.restaurantservice.infra.persistence.document.restaurant.RestaurantUserDocument;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class RestaurantMapper {

    public Restaurant toDomain(RestaurantDocument doc) {

        List<Menu> menus = doc.getMenus() == null
                ? List.of()
                : doc.getMenus().stream()
                .map(this::mapMenu)
                .toList();

        return Restaurant.reconstitute(
                doc.getId(),
                doc.getBusinessName(),
                doc.getCnpj(),
                doc.getCuisineType(),
                doc.getAddressBaseId(),
                doc.getNumberStreet(),
                doc.getComplement(),
                mapOpeningHours(doc.getOpeningHours()),
                menus,
                mapUsers(doc.getUsers())
        );
    }

    protected Menu mapMenu(MenuDocument md) {

        List<MenuItem> items = md.getItems() == null
                ? List.of()
                : md.getItems().stream()
                .map(this::mapMenuItem)
                .toList();

        return Menu.reconstitute(
                md.getId(),
                md.getName(),
                items
        );
    }

    protected MenuItem mapMenuItem(MenuItemDocument mi) {
        return MenuItem.reconstitute(
                mi.getId(),
                mi.getName(),
                mi.getDescription(),
                mi.getPrice(),
                mi.isInRestaurantOnly(),
                mi.getPhotograph()
        );
    }

    protected List<OpeningHours> mapOpeningHours(List<OpeningHoursDocument> docs) {
        if (docs == null) return List.of();

        return docs.stream()
                .map(d -> new OpeningHours(
                        d.getDayOfWeek(),
                        d.getOpenTime(),
                        d.getCloseTime(),
                        d.isClosed()
                ))
                .toList();
    }

    private List<RestaurantUser> mapUsers(List<RestaurantUserDocument> docs) {
        if (docs == null) return List.of();

        return docs.stream()
                .map(d -> new RestaurantUser(
                        d.getUserId(),
                        RestaurantRole.valueOf(d.getRole())
                ))
                .toList();
    }

    public RestaurantDocument toDocument(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantDocument doc = new RestaurantDocument();

        doc.setId(restaurant.getId());
        doc.setBusinessName(restaurant.getBusinessName());
        doc.setCnpj(restaurant.getCnpj());
        doc.setCuisineType(restaurant.getCuisineType());
        doc.setAddressBaseId(restaurant.getAddressBaseId());
        doc.setNumberStreet(restaurant.getNumberStreet());
        doc.setComplement(restaurant.getComplement());

        if (restaurant.getUsers() != null) {
            doc.setUsers(
                    restaurant.getUsers().stream()
                            .map(u -> new RestaurantUserDocument(
                                    u.getUserId(),
                                    u.getRole().name()
                            ))
                            .toList()
            );
        }

        // opening hours
        if (restaurant.getOpeningHours() != null) {
            doc.setOpeningHours(
                    restaurant.getOpeningHours().stream()
                            .map(oh -> new OpeningHoursDocument(
                                    oh.getDayOfWeek(),
                                    oh.getOpenTime(),
                                    oh.getCloseTime(),
                                    oh.isClosed()
                            ))
                            .toList()
            );
        }

        // menus
        if (restaurant.getMenus() != null) {
            doc.setMenus(
                    restaurant.getMenus().stream()
                            .map(menu -> {
                                MenuDocument md = new MenuDocument();
                                md.setId(menu.getId());
                                md.setName(menu.getName());

                                if (menu.getItems() != null) {
                                    md.setItems(
                                            menu.getItems().stream()
                                                    .map(item -> new MenuItemDocument(
                                                            item.getId(),
                                                            item.getName(),
                                                            item.getDescription(),
                                                            item.getPrice(),
                                                            item.isInRestaurantOnly(),
                                                            item.getPhotograph()
                                                    ))
                                                    .toList()
                                    );
                                }

                                return md;
                            })
                            .toList()
            );
        }

        return doc;
    }

}



