package br.com.foodhub.userservice.core.application.usecase.user;

import br.com.foodhub.userservice.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserResultDTO;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserGateway gateway;

    public UserResultDTO execute(UpdateUserDTO dto, String id) {
        log.info("Atualizando usuário: " + id);

        User user = gateway.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

        if (dto.name() != null && !dto.name().isEmpty()) {
            user.changeName(dto.name());
        }
        if (dto.email() != null && !dto.email().isEmpty()) {
            String oldEmail = user.getEmail();
            user.changeEmail(dto.email());

            if (!user.getEmail().equals(oldEmail)) {
                if (gateway.existsByEmail(user.getEmail())) {
                    throw new ResourceConflictException("Email já cadastrado");
                }
            }
        }

        if (dto.phone() != null && !dto.phone().isBlank()) {

            String oldPhone = user.getPhone();

            user.changePhone(dto.phone());

            if (!user.getPhone().equals(oldPhone)) {
                if (gateway.existsByPhone(user.getPhone())) {
                    throw new ResourceConflictException("Telefone já cadastrado.");
                }
            }
        }

        User saved = gateway.save(user);
        return UserResultDTO.from(saved);


    }
}
