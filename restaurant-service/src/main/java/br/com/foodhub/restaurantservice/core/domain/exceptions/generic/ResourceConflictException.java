package br.com.foodhub.restaurantservice.core.domain.exceptions.generic;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
