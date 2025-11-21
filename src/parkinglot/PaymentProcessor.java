package parkinglot;

import java.util.Scanner;

public interface PaymentProcessor {
    boolean processPayment(String paymentMethod, double amount, Ticket ticket);
}
