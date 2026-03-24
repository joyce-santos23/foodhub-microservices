package br.com.foodhub.userservice.infra.web.mapper.user;

import br.com.foodhub.userservice.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserResultDTO;
import br.com.foodhub.userservice.infra.web.payload.user.CreateUserInput;
import br.com.foodhub.userservice.infra.web.payload.user.UpdateUserInput;
import br.com.foodhub.userservice.infra.web.payload.user.UserResponsePayload;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {

    public static UserRequestDTO toDTO(CreateUserInput input) {
        return new UserRequestDTO(
                input.name(),
                input.email(),
                input.phone(),
                input.cpf(),
                input.password(),
                input.userTypeId()
        );
    }

    public static UserResponsePayload toPayload(UserResultDTO dto) {
        return new UserResponsePayload(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.phone(),
                dto.cpf(),
                dto.userTypeId()
        );
    }

    public static UpdateUserDTO toUpdateDTO(UpdateUserInput input) {
        return new UpdateUserDTO(
                input.name(),
                input.email(),
                input.phone()
        );
    }
}
