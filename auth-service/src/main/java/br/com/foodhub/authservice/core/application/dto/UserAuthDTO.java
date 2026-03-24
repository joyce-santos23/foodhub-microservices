package br.com.foodhub.authservice.core.application.dto;

public record UserAuthDTO(
        String id,
        String email,
        String password,
        String role
) {}