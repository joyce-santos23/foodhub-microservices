package br.com.foodhub.orderservice.infra.integration.grpc;

import br.com.foodhub.contracts.restaurant.RestaurantServiceGrpc;
import br.com.foodhub.contracts.user.UserServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel restaurantChannel() {
        return ManagedChannelBuilder
                .forAddress("restaurant-service", 9090) // nome do container
                .usePlaintext()
                .build();
    }

    @Bean
    public RestaurantServiceGrpc.RestaurantServiceBlockingStub restaurantStub(
            ManagedChannel channel) {
        return RestaurantServiceGrpc.newBlockingStub(channel);
    }
}
