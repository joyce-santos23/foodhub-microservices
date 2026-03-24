package br.com.foodhub.orderservice.core.domain.exceptions.generic;

public class ResourceNotFoundException extends DomainException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
