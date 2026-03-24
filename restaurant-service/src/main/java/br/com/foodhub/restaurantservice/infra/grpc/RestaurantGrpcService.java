package br.com.foodhub.restaurantservice.infra.grpc;

import br.com.foodhub.contracts.restaurant.*;
import br.com.foodhub.restaurantservice.core.application.dto.menu.items.MenuItemsIntegrationResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.application.usecase.menu.items.ListMenuItemsIntegrationUseCase;
import br.com.foodhub.restaurantservice.core.application.usecase.restaurant.CheckUserRestaurantAccessUseCase;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class RestaurantGrpcService extends RestaurantServiceGrpc.RestaurantServiceImplBase {

    private final ListMenuItemsIntegrationUseCase useCase;
    private final CheckUserRestaurantAccessUseCase checkUser;
    private final RestaurantGateway restaurantGateway;

    @Override
    public void validateRestaurant(
            ValidateRestaurantRequest request,
            StreamObserver<ValidateRestaurantResponse> responseObserver
    ) {

        boolean exists = restaurantGateway.findById(request.getRestaurantId()).isPresent();

        ValidateRestaurantResponse response =
                ValidateRestaurantResponse.newBuilder()
                        .setExists(exists)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findMenuItems(
            MenuItemsRequest request,
            StreamObserver<MenuItemsResponse> responseObserver
    ) {

        List<MenuItemsIntegrationResultDTO> items =
                useCase.execute(
                        request.getRestaurantId(),
                        request.getMenuId(),
                        request.getItemIdsList()
                );

        MenuItemsResponse response = MenuItemsResponse.newBuilder()
                .addAllItems(map(items))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<MenuItem> map(List<MenuItemsIntegrationResultDTO> items) {

        return items.stream()
                .map(item ->
                        MenuItem.newBuilder()
                                .setId(item.id())
                                .setName(item.name())
                                .setPrice(item.price().doubleValue())
                                .build()
                )
                .toList();
    }

    @Override
    public void isUserLinkedToRestaurant(UserRestaurantRequest request,
                                         StreamObserver<UserRestaurantResponse> responseObserver) {

        boolean linked = checkUser.execute(
                request.getUserId(),
                request.getRestaurantId()
        );

        responseObserver.onNext(
                UserRestaurantResponse.newBuilder()
                        .setLinked(linked)
                        .build()
        );

        responseObserver.onCompleted();
    }
}