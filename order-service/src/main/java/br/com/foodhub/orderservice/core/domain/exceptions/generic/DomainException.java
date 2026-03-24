package br.com.foodhub.orderservice.core.domain.exceptions.generic;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
