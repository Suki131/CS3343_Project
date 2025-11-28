package parkinglot;
import java.util.Map;
import java.util.Scanner;

public class PaymentInvoker {
    private PaymentProcessor paymentProcessor;

    private final Map<String, PaymentProcessor> commandList;
    private final static PaymentInvoker instance = new PaymentInvoker();

    private PaymentInvoker() {
        commandList = Map.of(
                "PAY_BY_ALIPAY", new AlipayHk_PaymentProcessor(),
                "PAY_BY_OCTOPUS", new Octopus_PaymentProcessor(),
                "PAY_BY_CREDIT_CARD", new CreditCard_PaymentProcessor()
        );
    }

    public boolean executeCommand(String cmdName, double amount, Ticket ticket) {
        PaymentProcessor paymentProcessor = commandList.get(cmdName);
        return paymentProcessor.processPayment(cmdName, amount, ticket);
    }

    public static PaymentInvoker getInstance() {
        return instance;
    }
}
