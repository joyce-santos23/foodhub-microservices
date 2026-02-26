package br.com.foodhub.userservice.core.application.port.address;

import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;

import java.util.Optional;

public interface AddressBaseGateway {
    Optional<AddressBase> findByCep(String cep);
    AddressBase save(AddressBase address);

    Optional<AddressBase> findById(String s);
}
