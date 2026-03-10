package br.com.foodhub.restaurantservice.core.application.port.user;

public interface UserIntegrationPort {

    boolean existsById(String userId);

    boolean canCreateRestaurant(String userId);
}
