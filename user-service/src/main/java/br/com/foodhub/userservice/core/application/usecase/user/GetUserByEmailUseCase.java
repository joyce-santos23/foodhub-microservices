package br.com.foodhub.userservice.core.application.usecase.user;

import br.com.foodhub.userservice.core.application.dto.user.UserAuthDTO;
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
public class GetUserByEmailUseCase {

    private final UserGateway userGateway;

    public UserAuthDTO execute(String email) {
        log.info("Iniciando execucao GetUserByEmail: " + email);

        User user = userGateway.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + email));

        return UserAuthDTO.from(user);
    }
}
