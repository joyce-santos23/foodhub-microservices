package br.com.foodhub.authservice.core.application.port;

import br.com.foodhub.authservice.core.application.dto.UserAuthDTO;

public interface UserAuthGateway {
    UserAuthDTO findByEmail(String email);
}
