package br.com.foodhub.orderservice.core.domain.exceptions.generic;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
