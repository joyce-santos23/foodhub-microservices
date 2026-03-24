package br.com.foodhub.userservice.core.application.usecase.user;

import br.com.foodhub.userservice.core.application.dto.user.UserResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UserGateway userGateway;

    public UserResultDTO execute(String id) {
        log.info("Iniciando execucao GetUserByIdUseCase: " + id);

        User user = userGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));

        return UserResultDTO.from(user);
    }
}
