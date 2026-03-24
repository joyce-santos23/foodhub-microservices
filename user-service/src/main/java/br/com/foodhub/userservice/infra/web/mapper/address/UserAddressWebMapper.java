package br.com.foodhub.userservice.infra.web.mapper.address;

import br.com.foodhub.userservice.core.application.dto.address.UpdateUserAddressDTO;
import br.com.foodhub.userservice.core.application.dto.address.UserAddressDTO;
import br.com.foodhub.userservice.infra.web.payload.address.CreateUserAddressInput;
import br.com.foodhub.userservice.infra.web.payload.address.UpdateUserAddressRequestPayload;
import org.springframework.stereotype.Component;

@Component
public class UserAddressWebMapper {

    public UserAddressDTO toCreateDto(CreateUserAddressInput input) {
        return new UserAddressDTO(
                input.cep(),
                input.number(),
                input.complement(),
                input.primary()
        );
    }

    public UpdateUserAddressDTO toUpdateDto(UpdateUserAddressRequestPayload input) {
        return new UpdateUserAddressDTO(
                input.number(),
                input.complement(),
                input.primary()
        );
    }
}