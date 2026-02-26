package br.com.foodhub.userservice.core.domain.exceptions.user;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String emailInválido) {
        super();
    }
}
