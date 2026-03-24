package br.com.foodhub.userservice.core.application.usecase.address;

import br.com.foodhub.userservice.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.userservice.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;
import br.com.foodhub.userservice.core.domain.entity.address.UserAddress;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserAddressUseCase {

    private final FindOrCreateAddressBaseUseCase findOrCreateAddressBaseUseCase;
    private final UserGateway userGateway;

    public UserAddressResultDTO execute(String userId, UserAddressDTO requestDTO) {
        log.info("Criando endereço para o usuario: " + userId);
        User user = userGateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));

        AddressBase addressBase = findOrCreateAddressBaseUseCase.execute(requestDTO.cep());

        UserAddress userAddress = new UserAddress(
                user.getId(),
                addressBase.getId(),
                requestDTO.number(),
                requestDTO.complement(),
                requestDTO.primary()
        );
        user.addAddress(userAddress);
        userGateway.save(user);

        log.info("Endereço criado para usuario: {}, dto: {}", userId, requestDTO);

        return UserAddressResultDTO.from(userAddress);
    }

}
