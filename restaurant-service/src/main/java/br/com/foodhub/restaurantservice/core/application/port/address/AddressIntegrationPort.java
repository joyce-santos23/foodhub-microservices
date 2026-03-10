package br.com.foodhub.restaurantservice.core.application.port.address;

public interface AddressIntegrationPort {

    String findOrCreateAddress(String cep);
}
