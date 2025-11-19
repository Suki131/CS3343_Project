package src.parkinglot;
import java.util.Scanner;

public class CmdMakePayment implements DriverCommand {

    TicketManager ticketManager = TicketManager.getInstance();
    BillingStrategy billingStrategy = null;
    PaymentInvoker paymentInvoker = PaymentInvoker.getInstance();

    @Override
    public void execute(String cmdName, Driver driver) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Calculating parking fee...");
        billingStrategy = HourlyBilling.getInstance();
        if (null != driver.getMembershipType() && driver.getMembershipExpiryDate() != null && driver.getMembershipExpiryDate().isAfter(java.time.LocalDateTime.now())) {
            switch (driver.getMembershipType()) {
                case NONE:
                    billingStrategy = HourlyBilling.getInstance();
                    break;
                case DAILY:
                    billingStrategy = DailyBilling.getInstance();
                    break;
                case MONTHLY:
                    billingStrategy = MonthlyBilling.getInstance();
                    break;
                case ANNUALLY:
                    billingStrategy = AnnualBilling.getInstance();
                    break;
            }
        }
        Ticket ticket = driver.getCurrentTicket();
        double parkingFee = billingStrategy.calculateBill(ticket);
        System.out.println("Your parking fee is: $" + parkingFee);
        System.out.println("Payment Method : \n 1. AliPay HK\n 2. Octopus Card\n 3. Credit Card \nSelect Payment method : ");
        String paymentMethod = scanner.nextLine();
        switch (paymentMethod) {
            case "1":
                paymentInvoker.executeCommand("PAY_BY_ALIPAY", parkingFee);
                ticket.changeStatus(TicketStatus.PAID);
                break;
            case "2":
                paymentInvoker.executeCommand("PAY_BY_OCTOPUS", parkingFee);
                ticket.changeStatus(TicketStatus.PAID);
                break;
            case "3":
                paymentInvoker.executeCommand("PAY_BY_CREDIT_CARD", parkingFee);
                ticket.changeStatus(TicketStatus.PAID);
                break;
            default:
                System.out.println("Invalid payment method selected.");
                System.out.println("=========================================================================================================");
                break;
        }


    }
    
}
