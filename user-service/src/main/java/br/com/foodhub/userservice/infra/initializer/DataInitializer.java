package br.com.foodhub.userservice.infra.initializer;

import br.com.foodhub.userservice.infra.persistence.document.address.AddressBaseDocument;
import br.com.foodhub.userservice.infra.persistence.document.user.UserDocument;
import br.com.foodhub.userservice.infra.persistence.document.user.UserTypeDocument;
import br.com.foodhub.userservice.infra.persistence.repository.address.AddressBaseMongoRepository;
import br.com.foodhub.userservice.infra.persistence.repository.user.UserMongoRepository;
import br.com.foodhub.userservice.infra.persistence.repository.usertype.UserTypeMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserTypeMongoRepository userTypeRepository;
    private final UserMongoRepository userRepository;
    private final AddressBaseMongoRepository addressBaseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // =========================
        // USER TYPES
        // =========================
        UserTypeDocument typeOwner = userTypeRepository.findByName("OWNER")
                .orElseGet(() -> userTypeRepository.save(
                        new UserTypeDocument(
                                "01c84aa21d2a4340800c11b593f2f77011",
                                "OWNER"
                        )
                ));

        UserTypeDocument typeCustomer = userTypeRepository.findByName("CUSTOMER")
                .orElseGet(() -> userTypeRepository.save(
                        new UserTypeDocument(
                                "01c84aa21d2a4340800c11b593f2f770",
                                "CUSTOMER"
                        )
                ));

        // =========================
        // OWNER
        // =========================
        if (!userRepository.existsByEmail("owner@foodhub.com")) {

            UserDocument owner = new UserDocument();

            owner.setId("239a64c2e8df4ba2a9e068d084969736");
            owner.setName("Owner Test");
            owner.setEmail("owner@foodhub.com");
            owner.setPhone("41999999999");
            owner.setCpf("12345678901");
            owner.setPassword(passwordEncoder.encode("senha123"));
            owner.setUserTypeId(typeOwner.getId());

            userRepository.save(owner);
        }

        // =========================
        // CUSTOMER
        // =========================
        if (!userRepository.existsByEmail("customer@foodhub.com")) {

            UserDocument customer = new UserDocument();

            customer.setId("69c2ee4ac1df24c267db27a3");
            customer.setName("Customer Test");
            customer.setEmail("customer@foodhub.com");
            customer.setPhone("41988888888");
            customer.setCpf("10987654321");
            customer.setPassword(passwordEncoder.encode("senha123"));
            customer.setUserTypeId(typeCustomer.getId());

            userRepository.save(customer);
        }

        // =========================
        // ADDRESS BASE
        // =========================

        if (!addressBaseRepository.existsByCep("80010000")) {

            AddressBaseDocument address = new AddressBaseDocument();

            address.setId("8e5437f31e19403c8cd9fc176d62e9d1");
            address.setCep("80010000");
            address.setStreet("Rua José Loureiro");
            address.setNeighborhood("Centro");
            address.setCity("Curitiba");
            address.setState("PR");
            address.setCountry("Brasil");

            addressBaseRepository.save(address);
        }
    }
}