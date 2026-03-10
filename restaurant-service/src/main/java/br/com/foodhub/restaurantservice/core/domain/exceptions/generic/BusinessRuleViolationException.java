package br.com.foodhub.restaurantservice.core.domain.exceptions.generic;

public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
