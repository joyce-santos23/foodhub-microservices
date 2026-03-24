package br.com.foodhub.userservice.core.application.usecase.address;

import br.com.foodhub.userservice.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListUserAddressUseCase {

    private final UserGateway gateway;

    public List<UserAddressResultDTO> execute(String userId) {

        log.info("Listando endereço do usuario: " + userId);

        User user = gateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + userId));

        return user.getAddresses().stream()
                .map(UserAddressResultDTO::from)
                .toList();
    }
}
