package br.com.foodhub.paymentservice.core.application.usecase;

import br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO;
import br.com.foodhub.paymentservice.core.application.port.ProccessPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProccessPaymentUsecase {
    private final ProccessPort proccessPort;

    public void execute(OrderCreatedEventDTO event) {

        proccessPort.process(event);
    }
}
