package br.com.foodhub.restaurantservice.core.application.port.address;

import br.com.foodhub.restaurantservice.core.application.dto.AddressBaseDTO;

import java.util.Map;
import java.util.Set;

public interface AddressIntegrationPort {

    String findOrCreateAddress(String cep);

    Map<String, AddressBaseDTO> getByIds(Set<String> ids);
}
