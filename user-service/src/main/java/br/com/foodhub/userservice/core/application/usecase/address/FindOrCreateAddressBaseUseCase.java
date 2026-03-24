package br.com.foodhub.userservice.core.application.usecase.address;

import br.com.foodhub.userservice.core.application.port.address.AddressBaseGateway;
import br.com.foodhub.userservice.core.application.port.cep.CepGateway;
import br.com.foodhub.userservice.core.domain.entity.address.AddressBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindOrCreateAddressBaseUseCase {

    private final AddressBaseGateway gateway;
    private final CepGateway cepGateway;

    public AddressBase execute(String cep) {
        log.info("Iniciando processo findOrCreateAddressBaseUseCase" + cep);

        String cepForSearch = cep.replaceAll("[^0-9]", "");

        return gateway.findByCep(cepForSearch)
                .orElseGet(() -> {
                    log.info("🔥 NÃO encontrou no banco, chamando CEP API");

                    AddressBase fromApi = cepGateway.lookup(cepForSearch);

                    log.info("🔥 Voltou da API");
                    return gateway.save(fromApi);
                });

    }
}
