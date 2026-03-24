package br.com.foodhub.orderservice.core.domain.exceptions.generic;

public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
