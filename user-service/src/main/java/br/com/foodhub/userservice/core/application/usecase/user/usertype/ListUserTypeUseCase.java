package br.com.foodhub.userservice.core.application.usecase.user.usertype;

import br.com.foodhub.userservice.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserTypeGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListUserTypeUseCase {

    private final UserTypeGateway gateway;

    public List<UserTypeResultDTO> execute() {
        log.info("Listando tipo usuario");
        return gateway.findAll().stream()
                .map(UserTypeResultDTO::from)
                .toList();
    }
}
