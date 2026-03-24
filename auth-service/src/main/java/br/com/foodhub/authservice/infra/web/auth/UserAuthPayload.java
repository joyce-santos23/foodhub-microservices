package br.com.foodhub.authservice.infra.web.auth;

public record UserAuthPayload(
        String id,
        String email,
        String password,
        String role
) {}