package br.com.foodhub.userservice.infra.gateway.usertype;

import br.com.foodhub.userservice.core.application.port.user.UserTypeGateway;
import br.com.foodhub.userservice.core.domain.entity.user.UserType;
import br.com.foodhub.userservice.infra.persistence.mapper.usertype.UserTypeMapper;
import br.com.foodhub.userservice.infra.persistence.repository.usertype.UserTypeMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserTypeGatewayImpl implements UserTypeGateway {

    private final UserTypeMongoRepository repository;
    private final UserTypeMapper mapper;

    @Override
    public Optional<UserType> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public UserType save(UserType userType) {
        return mapper.toDomain(
                repository.save(
                        mapper.toDocument(userType)
                )
        );
    }

    @Override
    public List<UserType> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(String userTypeId) {
        repository.deleteById(userTypeId);
    }

    @Override
    public Optional<UserType> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByName(String normalizedName) {
        return repository.existsByName(normalizedName);
    }
}