package br.com.foodhub.userservice.core.application.dto.address;


import br.com.foodhub.userservice.core.domain.entity.address.UserAddress;

public record UserAddressResultDTO(
        String id,
        String userId,
        String addressBaseId,
        boolean primary,
        String number,
        String complement
) {
    public static UserAddressResultDTO from(UserAddress address) {
        return new UserAddressResultDTO(
                address.getId(),
                address.getUserId(),
                address.getAddressId(),
                address.isPrimary(),
                address.getNumber(),
                address.getComplement()
        );
    }
}
