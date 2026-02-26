package br.com.foodhub.userservice.infra.persistence.mapper.user;

import br.com.foodhub.userservice.core.domain.entity.address.UserAddress;
import br.com.foodhub.userservice.core.domain.entity.user.User;
import br.com.foodhub.userservice.core.domain.entity.user.UserType;
import br.com.foodhub.userservice.infra.persistence.document.user.UserAddressDocument;
import br.com.foodhub.userservice.infra.persistence.document.user.UserDocument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    /*
     * Document → Domain
     */
    public User toDomain(UserDocument doc, UserType userType) {

        User user = User.reconstitute(
                doc.getId(),
                doc.getName(),
                doc.getEmail(),
                doc.getPhone(),
                doc.getCpf(),
                doc.getPassword(),
                userType
        );

        if (doc.getAddresses() != null) {
            doc.getAddresses().forEach(ad ->
                    user.addAddress(
                            UserAddress.reconstitute(
                                    ad.getId(),
                                    user.getId(),
                                    ad.getAddressBaseId(),
                                    ad.getNumber(),
                                    ad.getComplement(),
                                    ad.isPrimary()
                            )
                    )
            );
        }

        return user;
    }

    /*
     * Domain → Document
     */
    public UserDocument toDocument(User user) {

        UserDocument doc = new UserDocument();

        doc.setId(user.getId());
        doc.setName(user.getName());
        doc.setEmail(user.getEmail());
        doc.setPhone(user.getPhone());
        doc.setCpf(user.getCpf());
        doc.setPassword(user.getPassword());

        doc.setUserTypeId(user.getUserType().getId());

        if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {

            List<UserAddressDocument> addressDocs = user.getAddresses()
                    .stream()
                    .map(a -> new UserAddressDocument(
                            a.getId(),
                            a.getAddressId(),
                            a.getNumber(),
                            a.getComplement(),
                            a.isPrimary()
                    ))
                    .toList();

            doc.setAddresses(addressDocs);

        } else {
            doc.setAddresses(new ArrayList<>());
        }

        return doc;
    }
}