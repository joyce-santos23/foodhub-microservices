package br.com.foodhub.authservice.infra.web.graphql.auth;

import br.com.foodhub.authservice.core.application.usecase.LoginUseCase;
import br.com.foodhub.authservice.infra.web.auth.AuthPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthResolver {

    private final LoginUseCase loginUseCase;

    @MutationMapping
    public AuthPayload login(
            @Argument String email,
            @Argument String password
    ) {

        String token = loginUseCase.execute(email, password);

        return new AuthPayload(token);
    }
}
