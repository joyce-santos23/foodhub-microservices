package br.com.foodhub.userservice.core.domain.exceptions.generic;

public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
