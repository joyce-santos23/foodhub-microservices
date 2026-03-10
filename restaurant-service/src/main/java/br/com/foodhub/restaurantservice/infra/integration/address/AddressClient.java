package br.com.foodhub.restaurantservice.infra.integration.address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userservice")
public interface AddressClient {

    @PostMapping("/addresses/find-or-create")
    AddressResponse findOrCreate(@RequestParam String cep);
}
