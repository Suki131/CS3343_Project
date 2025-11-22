package parkinglot;
import java.util.Scanner;

public class CmdMakePayment implements DriverCommand {

    TicketManager ticketManager = TicketManager.getInstance();
    BillingStrategy billingStrategy = null;
    PaymentInvoker paymentInvoker = PaymentInvoker.getInstance();

    @Override
    public void execute(String cmdName, Driver driver) {
        Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("Calculating parking fee...");
        billingStrategy = HourlyBilling.getInstance();
        if (null != driver.getMembershipType() && driver.getMembershipExpiryDate() != null && driver.getMembershipExpiryDate().isAfter(java.time.LocalDateTime.now())) {
            if (driver.getMembershipType() == MembershipType.NONE) {
                billingStrategy = HourlyBilling.getInstance();
            } else if (driver.getMembershipType() == MembershipType.DAILY) {
                billingStrategy = DailyBilling.getInstance();
            } else if (driver.getMembershipType() == MembershipType.MONTHLY) {
                billingStrategy = MonthlyBilling.getInstance();
            } else {
                billingStrategy = AnnualBilling.getInstance();
            }
        }
        Ticket ticket = driver.getCurrentTicket();
        double parkingFee = billingStrategy.calculateBill(ticket);
        System.out.println("Your parking fee is: $" + parkingFee);
        System.out.println("Payment Method : \n 1. AliPay HK\n 2. Octopus Card\n 3. Credit Card \nSelect Payment method : ");
		String paymentMethod = scanner.nextLine();
		switch (paymentMethod) {
			   case "1":
			       paymentInvoker.executeCommand("PAY_BY_ALIPAY", parkingFee, ticket);
			       break;
			   case "2":
			       paymentInvoker.executeCommand("PAY_BY_OCTOPUS", parkingFee, ticket);
			       break;
			   case "3":
			       paymentInvoker.executeCommand("PAY_BY_CREDIT_CARD", parkingFee, ticket);
			       break;
			   default:
			       System.out.println("Invalid payment method selected.");
			       System.out.println("=========================================================================================================");
			       break;
			}


    }
    
}
