package br.com.foodhub.restaurantservice.infra.web.payload.restaurant;


import jakarta.validation.constraints.NotBlank;

public record RestaurantRequestInput(

        @NotBlank
        String businessName,

        @NotBlank
        String cnpj,

        @NotBlank
        String cuisineType,

        @NotBlank
        String cep,

        @NotBlank
        String numberStreet,
        String complement
) {}
