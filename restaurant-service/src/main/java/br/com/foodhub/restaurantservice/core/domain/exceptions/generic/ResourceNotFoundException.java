package br.com.foodhub.restaurantservice.core.domain.exceptions.generic;

public class ResourceNotFoundException extends DomainException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
