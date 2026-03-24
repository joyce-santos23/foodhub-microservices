package br.com.foodhub.userservice.infra.persistence.repository.address;

import br.com.foodhub.userservice.infra.persistence.document.address.AddressBaseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AddressBaseMongoRepository extends MongoRepository<AddressBaseDocument, String> {
    Optional<AddressBaseDocument> findByCep(String cep);

    boolean existsByCep(String cep);
}
