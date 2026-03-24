package br.com.foodhub.paymentservice.core.application.exception;

public class PaymentUnexpectedException extends RuntimeException {
    public PaymentUnexpectedException(String message) {
        super(message);
    }
}
