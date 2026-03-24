package br.com.foodhub.authservice.core.application.port;

public interface JwtGateway {
    String generateToken(String id, String role);
}
