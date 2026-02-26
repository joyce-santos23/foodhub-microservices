package br.com.foodhub.userservice.core.application.port.cep;

import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;

public interface CepGateway {
    AddressBase lookup(String cep);
}
