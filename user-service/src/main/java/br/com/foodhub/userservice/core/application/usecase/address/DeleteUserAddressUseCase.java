package br.com.foodhub.userservice.core.application.usecase.address;

import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserAddressUseCase {

    private final UserGateway gateway;

    public void execute(String userId, String userAddressId) {
        log.info("Deletando endereço: " + userAddressId);

        User user = gateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        user.removeAddress(userAddressId);

        gateway.save(user);
    }
}
