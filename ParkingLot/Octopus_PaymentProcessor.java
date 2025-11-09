import java.util.Scanner;

public class Octopus_PaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(String paymentMethod, double amount) {
        System.out.println("Please enter your Octopus Card number:");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();
        System.out.println("Processing Octopus payment of amount: " + amount);

        System.out.println("Payment of " + amount + " via Octopus successful for card number: " + cardNumber);
    }

}
