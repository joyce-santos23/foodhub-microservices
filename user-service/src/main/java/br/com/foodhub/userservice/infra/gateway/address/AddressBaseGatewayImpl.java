package br.com.foodhub.userservice.infra.gateway.address;

import br.com.foodhub.userservice.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;
import br.com.foodhub.userservice.infra.persistence.mapper.address.AddressBaseMapper;
import br.com.foodhub.userservice.infra.persistence.repository.address.AddressBaseMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressBaseGatewayImpl implements AddressBaseGateway {

    private final AddressBaseMongoRepository repository;
    private final AddressBaseMapper mapper;

    @Override
    public Optional<AddressBase> findByCep(String cep) {
        return repository.findByCep(cep)
                .map(mapper::toDomain);
    }

    @Override
    public AddressBase save(AddressBase address) {
        var document = mapper.toDocument(address);
        var saved = repository.save(document);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<AddressBase> findById(String s) {
        return repository.findById(s)
                .map(mapper::toDomain);
    }
}
