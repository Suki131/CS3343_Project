import java.util.Map;

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

    public void invokePayment(String paymentMethod, double amount) {
        paymentProcessor.processPayment(paymentMethod, amount);
    }

    public void executeCommand(String cmdName, double amount) {
        PaymentProcessor paymentProcessor = commandList.get(cmdName);
        if (paymentProcessor != null) {
            paymentProcessor.processPayment(cmdName, amount);
        }
    }

    public static PaymentInvoker getInstance() {
        return instance;
    }
}
