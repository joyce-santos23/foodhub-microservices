package br.com.foodhub.userservice.infra.web.mapper.user;

import br.com.foodhub.userservice.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.userservice.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.userservice.infra.web.payload.user.usertype.UserTypeInput;
import br.com.foodhub.userservice.infra.web.payload.user.usertype.UserTypeResponsePayload;
import org.springframework.stereotype.Component;

@Component
public class UserTypeWebMapper {

    public static UserTypeRequestDTO toDTO(UserTypeInput input) {
        return new UserTypeRequestDTO(input.name());
    }

    public static UserTypeResponsePayload toPayload(UserTypeResultDTO dto) {
        return new UserTypeResponsePayload(
                dto.id(),
                dto.name()
        );
    }
}
