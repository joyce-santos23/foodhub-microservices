package br.com.foodhub.restaurantservice.infra.integration.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {
    private String id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
