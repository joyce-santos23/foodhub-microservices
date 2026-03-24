package br.com.foodhub.userservice.core.application.usecase.address;

import br.com.foodhub.userservice.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.userservice.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.domain.entity.address.UserAddress;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import br.com.foodhub.userservice.core.domain.exceptions.user.AddressNotBelongsToUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserAddressUseCase {

    private final UserGateway gateway;

    public UserAddressResultDTO execute(
            String userId,
            String userAddressId,
            UpdateUserAddressDTO dto
            ) {
        log.info("Atualizando endereço: " + userAddressId);

        User user = gateway.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com ID: " + userId
                ));

        UserAddress address = user.getAddresses().stream()
                .filter(a -> a.getId().equals(userAddressId))
                .findFirst()
                .orElseThrow(AddressNotBelongsToUserException::new);

        address.update(
                dto.number(),
                dto.complement()
        );

        if (dto.primary()) {
            user.definePrimaryAddress(address.getId());
        }
        gateway.save(user);

        return UserAddressResultDTO.from(address);
    }
}
