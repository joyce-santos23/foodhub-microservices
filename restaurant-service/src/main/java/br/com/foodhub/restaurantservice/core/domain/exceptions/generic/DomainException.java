package br.com.foodhub.restaurantservice.core.domain.exceptions.generic;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
