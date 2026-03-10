package br.com.foodhub.restaurantservice.core.domain.exceptions.restaurant;

public class InvalidCnpjException extends RuntimeException {
    public InvalidCnpjException() {
        super("CNPJ inválido.");
    }
}
