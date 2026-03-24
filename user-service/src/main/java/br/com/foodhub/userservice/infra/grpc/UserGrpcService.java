package br.com.foodhub.userservice.infra.grpc;

import br.com.foodhub.contracts.user.*;
import br.com.foodhub.userservice.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.userservice.core.application.port.user.UserGateway;
import br.com.foodhub.userservice.core.application.usecase.address.FindOrCreateAddressBaseUseCase;
import br.com.foodhub.userservice.core.application.usecase.user.GetUserByEmailUseCase;
import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserGateway userGateway;
    private final AddressBaseGateway addressGateway;
    private final FindOrCreateAddressBaseUseCase addressUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;

    @Override
    public void canCreateRestaurant(
            CanCreateRestaurantRequest request,
            StreamObserver<CanCreateRestaurantResponse> responseObserver
    ) {
        log.info("🚀 RECEBI chamada gRPC para userId: " + request.getUserId());

        User user = userGateway.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        boolean allowed = user.getUserType().isOwner();

        CanCreateRestaurantResponse response =
                CanCreateRestaurantResponse.newBuilder()
                        .setAllowed(allowed)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findOrCreateAddressByCep(
            AddressByCepRequest request,
            StreamObserver<AddressResponse> responseObserver
    ) {

        AddressBase address =
                addressUseCase.execute(request.getCep());

        AddressResponse response =
                AddressResponse.newBuilder()
                        .setId(address.getId())
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAddressById(
            AddressByIdRequest request,
            StreamObserver<AddressResponse> responseObserver
    ) {

        log.info("📍 Buscando endereço por ID: {}", request.getId());

        AddressBase address = addressGateway.findById(request.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endereço não encontrado")
                );

        AddressResponse response =
                AddressResponse.newBuilder()
                        .setId(address.getId())
                        .setCep(address.getCep())
                        .setStreet(address.getStreet())
                        .setNeighborhood(address.getNeighborhood())
                        .setCity(address.getCity())
                        .setState(address.getState())
                        .setCountry(address.getCountry())
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserByEmail(
            GetUserByEmailRequest request,
            StreamObserver<GetUserByEmailResponse> responseObserver
    ) {

        var user = getUserByEmailUseCase.execute(request.getEmail());

        GetUserByEmailResponse response = GetUserByEmailResponse.newBuilder()
                .setId(user.id())
                .setEmail(user.email())
                .setPassword(user.password())
                .setRole(user.role())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}