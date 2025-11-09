package src.parkinglot;

public interface PaymentProcessor {
    void processPayment(String paymentMethod, double amount);
}
