package br.com.foodhub.restaurantservice.infra.grpc;

import br.com.foodhub.contracts.user.*;
import br.com.foodhub.restaurantservice.core.application.dto.AddressBaseDTO;
import br.com.foodhub.restaurantservice.core.application.port.address.AddressIntegrationPort;
import br.com.foodhub.restaurantservice.core.application.port.user.UserIntegrationPort;
import br.com.foodhub.restaurantservice.infra.web.payload.address.AddressBaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserGrpcClient
        implements UserIntegrationPort, AddressIntegrationPort {


    private final UserServiceGrpc.UserServiceBlockingStub stub;

    @Override
    public boolean canCreateRestaurant(String userId) {

        log.info("🔥 Chamando gRPC - canCreateRestaurant para userId: " + userId);

        CanCreateRestaurantRequest request =
                CanCreateRestaurantRequest.newBuilder()
                        .setUserId(userId)
                        .build();

        return stub
                .canCreateRestaurant(request)
                .getAllowed();
    }

    @Override
    public String findOrCreateAddress(String cep) {

        AddressByCepRequest request =
                AddressByCepRequest.newBuilder()
                        .setCep(cep)
                        .build();

        return stub
                .findOrCreateAddressByCep(request)
                .getId();
    }

    @Override
    public Map<String, AddressBaseDTO> getByIds(Set<String> ids) {

        return ids.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        this::getById
                ));
    }


    private AddressBaseDTO getById(String id) {

        AddressByIdRequest request =
                AddressByIdRequest.newBuilder()
                        .setId(id)
                        .build();

        AddressResponse response = stub
                .getAddressById(request);

        return new AddressBaseDTO(
                response.getId(),
                response.getCep(),
                response.getStreet(),
                response.getNeighborhood(),
                response.getCity(),
                response.getState(),
                response.getCountry()
        );
    }

}