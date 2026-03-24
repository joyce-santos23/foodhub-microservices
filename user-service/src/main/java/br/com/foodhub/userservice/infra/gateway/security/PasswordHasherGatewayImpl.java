package br.com.foodhub.userservice.infra.gateway.security;

import br.com.foodhub.userservice.core.application.port.security.PasswordHasherGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHasherGatewayImpl implements PasswordHasherGateway {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("HASH GERADO: " + encoded);
        return encoded;
    }
}
