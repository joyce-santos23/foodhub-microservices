package br.com.foodhub.paymentservice.core.application.port;

import br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO;

public interface ProccessPort {
        void process(OrderCreatedEventDTO event);
}
