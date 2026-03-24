package br.com.foodhub.restaurantservice.core.application.port.user;

public interface UserIntegrationPort {

    boolean canCreateRestaurant(String userId);
}
