package br.com.foodhub.userservice.infra.web.graphql.user;

import br.com.foodhub.userservice.core.application.dto.user.usertype.UserTypeRequestDTO;
import br.com.foodhub.userservice.core.application.dto.user.usertype.UserTypeResultDTO;
import br.com.foodhub.userservice.core.application.usecase.user.usertype.*;
import br.com.foodhub.userservice.infra.web.mapper.user.UserTypeWebMapper;
import br.com.foodhub.userservice.infra.web.payload.user.UserResponsePayload;
import br.com.foodhub.userservice.infra.web.payload.user.usertype.UserTypeInput;
import br.com.foodhub.userservice.infra.web.payload.user.usertype.UserTypeResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserTypeResolver {

    private final CreateUserTypeUseCase createUseCase;
    private final UpdateUserTypeUseCase updateUseCase;
    private final DeleteUserTypeUseCase deleteUseCase;
    private final ListUserTypeUseCase listUseCase;
    private final FindUserTypeByIdUseCase findUserTypeByIdUseCase;


    @MutationMapping
    @PreAuthorize("isAuthenticated() and !hasAuthority('CUSTOMER')")
    public UserTypeResponsePayload createUserType(@Argument @Valid UserTypeInput input) {

        UserTypeRequestDTO dto = UserTypeWebMapper.toDTO(input);

        UserTypeResultDTO result = createUseCase.execute(dto);

        return UserTypeWebMapper.toPayload(result);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated() and !hasAuthority('CUSTOMER')")
    public UserTypeResponsePayload updateUserType(
            @Argument String id,
            @Argument UserTypeInput input
    ) {

        UserTypeRequestDTO dto = UserTypeWebMapper.toDTO(input);

        UserTypeResultDTO result = updateUseCase.execute(id, dto);

        return UserTypeWebMapper.toPayload(result);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated() and !hasAuthority('CUSTOMER')")
    public Boolean deleteUserType(@Argument String id) {
        deleteUseCase.execute(id);
        return true;
    }

    @QueryMapping
    public List<UserTypeResponsePayload> listUserTypes() {

        return listUseCase.execute().stream()
                .map(UserTypeWebMapper::toPayload)
                .toList();
    }

    @SchemaMapping(typeName = "User", field = "userType")
    public UserTypeResponsePayload getUserType(UserResponsePayload user) {

        UserTypeResultDTO dto = findUserTypeByIdUseCase.execute(user.userTypeId());

        return UserTypeWebMapper.toPayload(dto);
    }
}
