package br.com.foodhub.paymentservice.infra.integration.procpag;

import br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO;
import br.com.foodhub.paymentservice.core.application.exception.PaymentTemporaryException;
import br.com.foodhub.paymentservice.core.application.exception.PaymentUnexpectedException;
import br.com.foodhub.paymentservice.core.application.port.ProccessPort;
import br.com.foodhub.paymentservice.infra.integration.kafka.PaymentProducer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcpagAdapter implements ProccessPort {

    private final RestTemplate restTemplate;
    private final PaymentProducer producer;

    @Value("${payment.api.url}")
    private String url;

    @Override
    @CircuitBreaker(name = "payment", fallbackMethod = "fallback")
    @Retry(name = "payment")
    public void process(OrderCreatedEventDTO event) {
            log.info("💰 processando pagamento orderId={} tentativa={}",
                    event.orderId(), event.attempts());

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("valor", event.amount().intValue());
            body.put("pagamento_id", event.orderId());
            body.put("cliente_id", event.userId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            int status = response.getStatusCode().value();

            if (status == 200 || status == 201) {
                producer.sendApproved(event);
                return;
            }

            if (status == 408 || status == 502) {
                throw new PaymentTemporaryException("erro temporário: " + status);
            }

            throw new PaymentUnexpectedException("erro inesperado: " + status);

        } catch (ResourceAccessException e) {
            throw new PaymentTemporaryException("timeout na chamada");
        }
    }

    public void fallback(OrderCreatedEventDTO event, Throwable t) {

        int attempts = event.attempts() + 1;

        log.error("⚠ fallback tentativa={} orderId={}",
                attempts, event.orderId(), t);

        if (attempts >= 3) {

            log.error("💀 enviando para DLQ orderId={}", event.orderId());

            producer.sendToDLQ(event);
            return;
        }

        producer.sendPending(new OrderCreatedEventDTO(
                event.orderId(),
                event.userId(),
                event.amount(),
                attempts
        ));
    }
}