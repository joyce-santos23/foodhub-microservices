package br.com.foodhub.restaurantservice.core.application.usecase.restaurant;

import br.com.foodhub.restaurantservice.core.application.dto.pagination.PageRequestDTO;
import br.com.foodhub.restaurantservice.core.application.dto.pagination.PageResultDTO;
import br.com.foodhub.restaurantservice.core.application.dto.restaurant.RestaurantResultDTO;
import br.com.foodhub.restaurantservice.core.application.port.restaurant.RestaurantGateway;
import br.com.foodhub.restaurantservice.core.domain.entity.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListAllRestaurantUseCase {

    private final RestaurantGateway gateway;

    public PageResultDTO<RestaurantResultDTO> execute(PageRequestDTO dto) {
        log.info("Iniciando busca de restaurantes");

        PageResultDTO<Restaurant> page =
                gateway.findAll(dto.page(), dto.size());

        return new PageResultDTO<>(
                page.content().stream()
                        .map(RestaurantResultDTO::from)
                        .toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );
    }
}

