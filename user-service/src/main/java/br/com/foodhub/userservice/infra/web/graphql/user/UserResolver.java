package br.com.foodhub.userservice.infra.web.graphql.user;

import br.com.foodhub.userservice.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.userservice.core.application.dto.user.UpdateUserDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserRequestDTO;
import br.com.foodhub.userservice.core.application.dto.user.UserResultDTO;
import br.com.foodhub.userservice.core.application.usecase.user.*;
import br.com.foodhub.userservice.infra.web.mapper.user.UserWebMapper;
import br.com.foodhub.userservice.infra.web.payload.pagination.PageResponsePayload;
import br.com.foodhub.userservice.infra.web.payload.user.CreateUserInput;
import br.com.foodhub.userservice.infra.web.payload.user.UpdateUserInput;
import br.com.foodhub.userservice.infra.web.payload.user.UserResponsePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserResolver {

    private final CreateUserUseCase useCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ListUserUseCase listUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    @MutationMapping
    public UserResponsePayload createUser(@Argument @Valid CreateUserInput input) {

        UserRequestDTO dto = UserWebMapper.toDTO(input);

        UserResultDTO result = useCase.execute(dto);

        return UserWebMapper.toPayload(result);
    }

    @MutationMapping
    @PreAuthorize("#id == authentication.principal")
    public UserResponsePayload updateUser(
            @AuthenticationPrincipal String id,
            @Argument UpdateUserInput input
    ) {
        UpdateUserDTO dto = UserWebMapper.toUpdateDTO(input);

        UserResultDTO result = updateUserUseCase.execute(dto, id);

        return UserWebMapper.toPayload(result);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('OWNER')")
    public PageResponsePayload<UserResponsePayload> listUsers(
            @Argument Integer page,
            @Argument Integer size
    ) {

        int pageValue = page != null ? page : 0;
        int sizeValue = size != null ? size : 10;

        var result = listUserUseCase.execute(
                new PageRequestDTO(pageValue, sizeValue)
        );

        return new PageResponsePayload<>(
                result.content().stream().map(UserWebMapper::toPayload).toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public UserResponsePayload me(@AuthenticationPrincipal String userId) {

        UserResultDTO result = getUserByIdUseCase.execute(userId);

        return UserWebMapper.toPayload(result);
    }
}
