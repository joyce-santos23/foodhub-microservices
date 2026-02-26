package br.com.foodhub.userservice.infra.persistence.repository.usertype;

import br.com.foodhub.userservice.infra.persistence.document.user.UserTypeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTypeMongoRepository
        extends MongoRepository<UserTypeDocument, String> {

    Optional<UserTypeDocument> findByName(String name);

    boolean existsByName(String normalizedName);
}
