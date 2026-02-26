package br.com.foodhub.userservice.core.application.usecase.user;


import br.com.foodhub.userservice.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserResultDTO;
import br.com.foodhub.userservice.core.application.port.security.PasswordHasherGateway;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.application.port.user.UserTypeGateway;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.entity.user.UserType;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;
    private final PasswordHasherGateway passwordHasherGateway;

    public UserResultDTO execute(UserRequestDTO dto) {

        if (userGateway.existsByEmail(dto.email())) {
            throw new ResourceConflictException("Email já cadastrado.");
        }

        if (userGateway.existsByPhone(dto.phone())) {
            throw new ResourceConflictException("Telefone já cadastrado.");
        }

        if (dto.cpf() != null && !dto.cpf().isBlank()
                && userGateway.existsByCpf(dto.cpf())) {
            throw new ResourceConflictException("CPF já cadastrado.");
        }

        UserType userType = userTypeGateway.findById(dto.userTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tipo de usuário inválido.")
                );

        String hashedPassword = passwordHasherGateway.hash(dto.password());

        User user = new User(
                dto.name(),
                dto.email(),
                dto.phone(),
                hashedPassword,
                userType
        );

        if (dto.cpf() != null && !dto.cpf().isBlank()) {
            user.defineCpf(dto.cpf());
        }

        User saved = userGateway.save(user);

        return UserResultDTO.from(saved);
    }
}
