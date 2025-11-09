package src.parkinglot;
import java.util.Scanner;

public class AlipayHk_PaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(String paymentMethod, double amount) {
        System.out.println("Please enter your Alipay HK account phone number:");
        Scanner scanner = new Scanner(System.in);
        String phoneNumber = scanner.nextLine();
        System.out.println("Processing Alipay HK payment of amount: " + amount);
        System.out.println("Payment of " + amount + " via Alipay HK successful for account: " + phoneNumber);
    }
}