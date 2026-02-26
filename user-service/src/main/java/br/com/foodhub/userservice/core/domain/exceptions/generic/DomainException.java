package br.com.foodhub.userservice.core.domain.exceptions.generic;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
