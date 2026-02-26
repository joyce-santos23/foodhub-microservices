package br.com.foodhub.userservice.infra.persistence.document.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "address_bases")
public class AddressBaseDocument {

    @Id
    private String id;
    @Indexed(unique = true)
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
}

