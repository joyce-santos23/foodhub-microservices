package br.com.foodhub.userservice.core.domain.exceptions.generic;

public class RequiredFieldException extends RuntimeException {
    public RequiredFieldException(String field) {
        super(field + " é obrigatório.");
    }
}
