package br.com.foodhub.userservice.infra.persistence.document.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_types")
public class UserTypeDocument {

    @Id
    private String id;

    private String name;
}

