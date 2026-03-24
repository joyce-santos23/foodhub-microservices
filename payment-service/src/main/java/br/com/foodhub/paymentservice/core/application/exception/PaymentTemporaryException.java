package br.com.foodhub.paymentservice.core.application.exception;

public class PaymentTemporaryException extends RuntimeException {
    public PaymentTemporaryException(String message) {
        super(message);
    }
}
