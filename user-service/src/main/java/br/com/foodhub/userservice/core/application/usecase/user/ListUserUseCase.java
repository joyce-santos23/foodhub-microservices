package br.com.foodhub.userservice.core.application.usecase.user;

import br.com.foodhub.userservice.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.userservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;

import br.com.foodhub.userservice.core.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListUserUseCase {

    private final UserGateway gateway;

    public PageResultDTO<UserResultDTO> execute(PageRequestDTO dto) {
        log.info("Iniciando lista de usuarios de usuários");

        PageResultDTO<User> page = gateway.findAll(dto.page(), dto.size());

        List<UserResultDTO> result = page.content().stream()
                .map(UserResultDTO::from)
                .toList();

        return new PageResultDTO<>(
                result,
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );

    }
}
