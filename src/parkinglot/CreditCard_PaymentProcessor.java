package src.parkinglot;
import java.util.Scanner;

public class CreditCard_PaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(String paymentMethod, double amount) {
        System.out.println("Please enter your Credit Card number:");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();
        System.out.println("Please enter your Credit Card CVV:");
        String cvv = scanner.nextLine();
        System.out.println("Please enter your Credit Card Expiry Date (MM/YY):");
        String expiryDate = scanner.nextLine();
        System.out.println("Processing Credit Card payment of amount: " + amount);

        System.out.println("Payment of " + amount + " via Credit Card successful for card number: " + cardNumber);
    }
}
