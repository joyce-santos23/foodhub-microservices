package br.com.foodhub.authservice.infra.grpc;


import br.com.foodhub.authservice.core.application.dto.UserAuthDTO;
import br.com.foodhub.authservice.core.application.port.UserAuthGateway;
import br.com.foodhub.contracts.user.GetUserByEmailRequest;
import br.com.foodhub.contracts.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGrpcClient implements UserAuthGateway {

    private final UserServiceGrpc.UserServiceBlockingStub stub;

    @Override
    public UserAuthDTO findByEmail(String email) {

        var response = stub.getUserByEmail(
                GetUserByEmailRequest.newBuilder()
                        .setEmail(email)
                        .build()
        );

        return new UserAuthDTO(
                response.getId(),
                response.getEmail(),
                response.getPassword(),
                response.getRole()
        );
    }
}
