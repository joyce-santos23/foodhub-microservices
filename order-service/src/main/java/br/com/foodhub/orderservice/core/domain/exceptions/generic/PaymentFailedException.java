package br.com.foodhub.orderservice.core.domain.exceptions.generic;

public class PaymentFailedException extends DomainException {
    public PaymentFailedException(String message) {
        super(message);
    }
}
