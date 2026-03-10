package br.com.foodhub.restaurantservice.infra.persistence.gateway.address;

import br.com.foodhub.restaurantservice.core.application.port.address.AddressIntegrationPort;
import br.com.foodhub.restaurantservice.infra.integration.address.AddressClient;
import br.com.foodhub.restaurantservice.infra.integration.address.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressIntegrationAdapter implements AddressIntegrationPort {

    private final AddressClient client;

    @Override
    public String findOrCreateAddress(String cep) {
        AddressResponse response = client.findOrCreate(cep);
        return response.getId();
    }
}
