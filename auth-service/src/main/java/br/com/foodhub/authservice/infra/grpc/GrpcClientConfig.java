package br.com.foodhub.authservice.infra.grpc;

import br.com.foodhub.contracts.user.UserServiceGrpc;
import io.grpc.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceStub(
            GrpcChannelFactory channelFactory) {

        Channel channel = io.grpc.ManagedChannelBuilder
                .forAddress("user-service", 9091)
                .usePlaintext()
                .build();

        return UserServiceGrpc.newBlockingStub(channel);
    }
}
