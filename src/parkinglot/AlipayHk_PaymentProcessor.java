package parkinglot;
import java.util.Scanner;

public class AlipayHk_PaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(String paymentMethod, double amount, Ticket ticket) {
    	Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("Please enter your Alipay HK account phone number:");
        String phoneNumber = scanner.nextLine();
		System.out.println("Processing Alipay HK payment of amount: " + amount);
		System.out.println("Payment of " + amount + " via Alipay HK successful for account: " + phoneNumber);
        ticket.changeStatus(TicketStatus.PAID);
        return ticket.getStatus() == TicketStatus.PAID;
    }
}