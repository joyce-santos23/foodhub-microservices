package br.com.foodhub.restaurantservice.core.domain.exceptions.generic;

public class RequiredFieldException extends RuntimeException {
    public RequiredFieldException(String field) {
        super(field + " é obrigatório.");
    }
}
