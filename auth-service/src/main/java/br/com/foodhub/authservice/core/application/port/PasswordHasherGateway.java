package br.com.foodhub.authservice.core.application.port;

public interface PasswordHasherGateway {
    boolean matches(String rawPassword, String hashedPassword);
}
