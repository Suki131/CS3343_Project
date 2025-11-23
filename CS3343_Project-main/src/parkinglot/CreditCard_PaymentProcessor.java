// CreditCard_PaymentProcessor.java
package parkinglot;

import java.util.Scanner;

public class CreditCard_PaymentProcessor implements PaymentProcessor {

    @Override
    public boolean processPayment(String paymentMethod, double amount, Ticket ticket) {
    	Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("Please enter your Credit Card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Please enter your Credit Card CVV:");
        String cvv = scanner.nextLine();

        System.out.println("Please enter your Credit Card Expiry Date (mm/yy):");
        String expiryDate = scanner.nextLine();

        System.out.println("Processing Credit Card payment of amount: " + amount);
        System.out.println("Payment of " + amount + " via Credit Card successful for card number: " + cardNumber);
        ticket.changeStatus(TicketStatus.PAID);
        return true;
    }
}