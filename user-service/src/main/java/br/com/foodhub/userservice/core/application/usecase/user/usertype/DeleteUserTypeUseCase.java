package br.com.foodhub.userservice.core.application.usecase.user.usertype;

import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.application.port.user.UserTypeGateway;
import br.com.foodhub.userservice.core.domain.entity.user.UserType;
import br.com.foodhub.userservice.core.domain.exceptions.generic.BusinessRuleViolationException;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserTypeUseCase {

    private final UserTypeGateway gateway;
    private final UserGateway userGateway;

    public void execute(String userTypeId) {

        log.info("Removendo tipo usuario" + userTypeId);

        UserType type = gateway.findById(userTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado.")
                );

        if (type.isSystemType()) {
            throw new BusinessRuleViolationException("Não é permitido excluir tipos de usuário do sistema."
            );
        }

        if (userGateway.existsUserWithUserType(userTypeId)) {
            throw new BusinessRuleViolationException("Não é permitido excluir tipos de usuário vinculados a usuários."
            );
        }
        gateway.delete(userTypeId);
    }
}
