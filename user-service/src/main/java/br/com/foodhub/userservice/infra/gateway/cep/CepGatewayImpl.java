package br.com.foodhub.userservice.infra.gateway.cep;

import br.com.foodhub.userservice.core.application.port.cep.CepGateway;
import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CepGatewayImpl implements CepGateway {

    private static final String URL =
            "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public AddressBase lookup(String cep) {

        CepResponse response =
                restTemplate.getForObject(URL, CepResponse.class, cep);

        if (response == null || response.getCep() == null) {
            throw new ResourceNotFoundException("CEP inválido");
        }

        return new AddressBase(
                response.getCep(),
                response.getLogradouro(),
                response.getBairro(),
                response.getLocalidade(),
                response.getUf(),
                "Brasil"
        );
    }

}
