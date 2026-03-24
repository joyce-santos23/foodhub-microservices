package br.com.foodhub.userservice.core.application.usecase.user.usertype;

import br.com.foodhub.userservice.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserTypeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserTypeByIdUseCase {

    private final UserTypeGateway userTypeGateway;

    public UserTypeResultDTO execute(String id) {
        return userTypeGateway.findById(id)
                .map(UserTypeResultDTO::from)
                .orElseThrow(() ->
                        new RuntimeException("Tipo de usuário não encontrado.")
                );
    }
}
