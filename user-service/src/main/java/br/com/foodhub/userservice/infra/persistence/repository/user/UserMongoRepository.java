package br.com.foodhub.userservice.infra.persistence.repository.user;


import br.com.foodhub.userservice.infra.persistence.document.user.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);

    boolean existsByUserTypeId(String userTypeId);
}
