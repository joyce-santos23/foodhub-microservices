package br.com.foodhub.userservice.core.application.port.security;

public interface PasswordHasherGateway {
    String hash(String rawPassword);
}
