package br.com.foodhub.userservice.infra.gateway.user;

import br.com.foodhub.userservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.application.port.user.UserTypeGateway;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.entity.user.UserType;
import br.com.foodhub.userservice.infra.persistence.document.user.UserDocument;
import br.com.foodhub.userservice.infra.persistence.mapper.user.UserMapper;
import br.com.foodhub.userservice.infra.persistence.repository.user.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

    private final UserMongoRepository repository;
    private final UserMapper mapper;
    private final UserTypeGateway userTypeGateway;

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public User save(User user) {

        UserDocument document = mapper.toDocument(user);
        UserDocument saved = repository.save(document);

        UserType userType = userTypeGateway
                .findById(saved.getUserTypeId())
                .orElseThrow();

        return mapper.toDomain(saved, userType);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id)
                .map(doc -> {
                    UserType userType = userTypeGateway
                            .findById(doc.getUserTypeId())
                            .orElseThrow();

                    return mapper.toDomain(doc, userType);
                });
    }

    @Override
    public PageResultDTO<User> findAll(int page, int size) {

        var pageable = PageRequest.of(page, size);
        var result = repository.findAll(pageable);

        var users = result.getContent()
                .stream()
                .map(doc -> {
                    UserType userType = userTypeGateway
                            .findById(doc.getUserTypeId())
                            .orElseThrow();

                    return mapper.toDomain(doc, userType);
                })
                .toList();

        return new PageResultDTO<>(
                users,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public boolean existsUserWithUserType(String userTypeId) {
        return repository.existsByUserTypeId(userTypeId);
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return repository.findByEmail(email)
                .map(doc -> {
                    UserType userType = userTypeGateway
                            .findById(doc.getUserTypeId())
                            .orElseThrow(() -> new RuntimeException("UserType não encontrado"));

                    return mapper.toDomain(doc, userType);
                });
    }
}