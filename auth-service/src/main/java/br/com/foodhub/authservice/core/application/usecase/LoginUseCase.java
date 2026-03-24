package br.com.foodhub.authservice.core.application.usecase;

import br.com.foodhub.authservice.core.application.dto.UserAuthDTO;
import br.com.foodhub.authservice.core.application.port.JwtGateway;
import br.com.foodhub.authservice.core.application.port.PasswordHasherGateway;
import br.com.foodhub.authservice.core.application.port.UserAuthGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserAuthGateway userGateway;
    private final JwtGateway jwtGateway;
    private final PasswordHasherGateway passwordHasher;

    public String execute(String email, String password) {

        UserAuthDTO user = userGateway.findByEmail(email);

        if (!passwordHasher.matches(password, user.password())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return jwtGateway.generateToken(user.id(), user.role());
    }
}