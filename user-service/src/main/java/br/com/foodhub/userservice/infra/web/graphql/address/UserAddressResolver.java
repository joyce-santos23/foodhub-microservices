package br.com.foodhub.userservice.infra.web.graphql.address;


import br.com.foodhub.userservice.core.application.dto.address.UserAddressResultDTO;
import br.com.foodhub.userservice.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.userservice.core.application.usecase.address.CreateUserAddressUseCase;
import br.com.foodhub.userservice.core.application.usecase.address.DeleteUserAddressUseCase;
import br.com.foodhub.userservice.core.application.usecase.address.ListUserAddressUseCase;
import br.com.foodhub.userservice.core.application.usecase.address.UpdateUserAddressUseCase;
import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;
import br.com.foodhub.userservice.infra.web.mapper.address.UserAddressWebMapper;
import br.com.foodhub.userservice.infra.web.payload.address.CreateUserAddressInput;
import br.com.foodhub.userservice.infra.web.payload.address.UpdateUserAddressRequestPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserAddressResolver {

    private final CreateUserAddressUseCase createUseCase;
    private final UpdateUserAddressUseCase updateUseCase;
    private final DeleteUserAddressUseCase deleteUseCase;
    private final ListUserAddressUseCase listUseCase;
    private final UserAddressWebMapper mapper;
    private final AddressBaseGateway addressBaseGateway;

    /* =======================
       QUERY
       ======================= */

    @QueryMapping
    public List<UserAddressResultDTO> listUserAddresses(
            @AuthenticationPrincipal String userId
    ) {
        return listUseCase.execute(userId);
    }

    /* =======================
       MUTATIONS
       ======================= */

    @MutationMapping
    public UserAddressResultDTO createUserAddress(
            @AuthenticationPrincipal String userId,
            @Argument @Valid CreateUserAddressInput input
    ) {
        return createUseCase.execute(
                userId,
                mapper.toCreateDto(input)
        );
    }

    @MutationMapping
    public UserAddressResultDTO updateUserAddress(
            @AuthenticationPrincipal String userId,
            @Argument String addressId,
            @Argument UpdateUserAddressRequestPayload input
    ) {
        return updateUseCase.execute(
                userId,
                addressId,
                mapper.toUpdateDto(input)
        );
    }

    @MutationMapping
    public Boolean deleteUserAddress(
            @AuthenticationPrincipal String userId,
            @Argument String addressId
    ) {
        deleteUseCase.execute(userId, addressId);
        return true;
    }

    @SchemaMapping(typeName = "UserAddress", field = "address")
    public AddressBase resolveAddress(UserAddressResultDTO dto) {
        return addressBaseGateway.findById(dto.addressBaseId())
                .orElseThrow();
    }
}