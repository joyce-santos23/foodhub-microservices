package br.com.foodhub.paymentservice.infra.integration.kafka;


import br.com.foodhub.contracts.event.OrderCreatedEventDTO;
import br.com.foodhub.paymentservice.core.application.usecase.ProccessPaymentUsecase;
import br.com.foodhub.paymentservice.infra.integration.kafka.mapper.PaymentEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRetryConsumer {

    private final ProccessPaymentUsecase usecase;

    @KafkaListener(topics = "pagamento.pendente")
    public void retry(OrderCreatedEventDTO event) throws InterruptedException {

        log.info("🔄 retry tentativa={} orderId={}",
                event.attempts(), event.orderId());

        Thread.sleep(2000L * event.attempts());

        var domain = PaymentEventMapper.toDomain(event);

        usecase.execute(domain);
    }
}