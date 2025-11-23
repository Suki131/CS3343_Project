package parkinglot;
import java.util.Scanner;

public class CmdAdjustFee implements StaffCommand {
    TicketManager ticketManager = TicketManager.getInstance();
    DriverManager driverManager = DriverManager.getInstance();
    BillingStrategy billingStrategy = null;

    @Override
    public void execute(String cmdName, Staff staff) {
    	Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("The following parking bill can change:");
        System.out.println("1. Hourly Billing");
        System.out.println("2. Daily Billing");
        System.out.println("3. Monthly Billing");
        System.out.println("4. Annual Billing");
        System.out.println("5. Exit");
        System.out.println("Enter the parking plan to adjust:");
        String parkingPlan = scanner.nextLine();
        double newFee = 0.0;
        boolean changed = false;
        String confirmation = "";
        switch (parkingPlan) {
            case "1":  
                parkingPlan = "Hourly";
                System.out.println("Enter new parking rate:");
                newFee = scanner.nextDouble();
                System.out.printf("Parking fee for plan '%s' adjusted to %.2f%n", parkingPlan, newFee);
                System.out.println("Confirm to continous (yes/no)");
                confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("yes")) {
                    changed = true;
                    billingStrategy = HourlyBilling.getInstance();
                    billingStrategy.setFeeRate(newFee);
                } else {
                	changed = false;
                    System.out.println("Confirmation failed.");
                }
                break;
            case "2":
                parkingPlan = "Daily";
                System.out.println("Enter new parking rate:");
                newFee = scanner.nextDouble();
                System.out.printf("Parking fee for plan '%s' adjusted to %.2f%n", parkingPlan, newFee);
                System.out.println("Confirm to continous (yes/no)");
                confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("yes")) {
                    changed = true;
                    billingStrategy = DailyBilling.getInstance();
                    billingStrategy.setFeeRate(newFee);
                } else {
                	changed = false;
                    System.out.println("Confirmation failed.");
                }
                break;
            case "3":
                parkingPlan = "Monthly";
                System.out.println("Enter new parking rate:");
                newFee = scanner.nextDouble();
                System.out.printf("Parking fee for plan '%s' adjusted to %.2f%n", parkingPlan, newFee);
                System.out.println("Confirm to continous (yes/no)");
                confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("yes")) {
                    changed = true;
                    billingStrategy = MonthlyBilling.getInstance();
                    billingStrategy.setFeeRate(newFee);
                } else {
                	changed = false;
                    System.out.println("Confirmation failed.");
                }
                break;
            case "4":
                parkingPlan = "Annually";
                System.out.println("Enter new parking rate:");
                newFee = scanner.nextDouble();
                System.out.printf("Parking fee for plan '%s' adjusted to %.2f%n", parkingPlan, newFee);
                System.out.println("Confirm to continous (yes/no)");
                confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("yes")) {
                    changed = true;
                    billingStrategy = AnnualBilling.getInstance();
                    billingStrategy.setFeeRate(newFee);
                } else {
                	changed = false;
                    System.out.println("Confirmation failed.");
                }
                break;
            case "5":
                System.out.println("=========================================================================================================");
                return;
            default:
                System.out.println("No this option");
        }
        if (changed) {
            System.out.println("The billing of " + parkingPlan + " has been changed to " + String.valueOf(newFee));
            System.out.println("=========================================================================================================");
        } else if (!changed){
            System.out.println("The billing of " + parkingPlan + " change failiure.");
            System.out.println("=========================================================================================================");
        }
    }
    
}
