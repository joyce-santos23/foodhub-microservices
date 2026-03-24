package br.com.foodhub.userservice.core.application.port.user;


import br.com.foodhub.userservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.userservice.core.domain.entity.user.User;

import java.util.Optional;

public interface UserGateway {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);

    User save(User user);

    Optional<User> findById(String id);

    PageResultDTO<User> findAll(int page, int size);

    boolean existsUserWithUserType(String userTypeId);

     Optional<User>findByEmail(String email);
}
