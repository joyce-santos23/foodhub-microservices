package br.com.foodhub.userservice.core.domain.exceptions.user;

public class AddressNotBelongsToUserException extends RuntimeException {
    public AddressNotBelongsToUserException() {
        super("Este endereço não pertence a este usuário");
    }
}
