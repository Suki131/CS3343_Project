package parkinglot;
import java.util.Scanner;

public class Octopus_PaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(String paymentMethod, double amount, Ticket ticket) {
    	Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("Please enter your Octopus Card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Processing Octopus payment of amount: " + amount);

        System.out.println("Payment of " + amount + " via Octopus successful for card number: " + cardNumber);

        ticket.changeStatus(TicketStatus.PAID);
        return true;
    }
    
}
