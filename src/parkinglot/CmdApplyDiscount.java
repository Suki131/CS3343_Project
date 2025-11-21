package parkinglot;
import java.util.Scanner;
public class CmdApplyDiscount implements StaffCommand {
    @Override
    public void execute(String cmdName, Staff staff) {
    	Scanner scanner = SmartParkingSystem.getScanner();
        DiscountStrategy discountStrategy = null;
        boolean continous1 = true;
        Vehicle vehicle = null;
        Driver driver = null;
        boolean continous2 = true;
        while (continous1){
            System.out.println("Please enter vehicle license plate to apply discount:");
            String licensePlate = scanner.nextLine();
            vehicle = ParkingManager.findVehicle(licensePlate);
            driver = vehicle != null ? vehicle.getOwnerDriver() : null;
            continous2 = true;
            if (vehicle == null) {
                System.out.println("Vehicle not found in the parking lot.");
                System.out.println("=========================================================================================================");
                while (continous2) {
                        System.out.print("Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ");
                        String choice = scanner.nextLine();
                        switch (choice) {
                                case "1":
                                    continous2 = false;
                                    System.out.println("=========================================================================================================");
                                    break;
                                case "2":
                                    System.out.println("=========================================================================================================");
                                    return;   
                                default:
                                    System.out.println("=========================================================================================================");
                                    System.out.println("****************** Please enter 1 or 2 ******************");
                            }
                    }
            } else {
                continous1 = false;
            }
        }

        Ticket ticket = TicketManager.getInstance().getEnteredTicket(vehicle);
        while (continous1) {
            if (ticket == null) {
                continous2 = true;
                System.out.println("No active ticket found for this vehicle.");
                System.out.println("=========================================================================================================");
                while (continous2) {
                        System.out.print("Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ");
                        String choice = scanner.nextLine();
                        switch (choice) {
                                case "1":
                                    continous2 = false;
                                    System.out.println("=========================================================================================================");
                                    break;
                                case "2":
                                    System.out.println("=========================================================================================================");
                                    return;   
                                default:
                                    System.out.println("=========================================================================================================");
                                    System.out.println("****************** Please enter 1 or 2 ******************");
                            }
                    }
            } else {
                continous1 = false;
            }
        }

        BillingStrategy billingStrategy = HourlyBilling.getInstance();
        ticket.setBillingStrategy(billingStrategy);
        if (null != driver.getMembershipType() && driver.getMembershipExpiryDate() != null && driver.getMembershipExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            switch (driver.getMembershipType()) {
                case NONE:
                    billingStrategy = HourlyBilling.getInstance();
                    ticket.setBillingStrategy(billingStrategy);
                    break;
                case DAILY:
                    billingStrategy = DailyBilling.getInstance();
                    ticket.setBillingStrategy(billingStrategy);
                    break;
                case MONTHLY:
                    billingStrategy = MonthlyBilling.getInstance();
                    ticket.setBillingStrategy(billingStrategy);
                    break;
                case ANNUALLY:
                    billingStrategy = AnnualBilling.getInstance();
                    ticket.setBillingStrategy(billingStrategy);
                    break;
                default:
                    billingStrategy = HourlyBilling.getInstance();
                    ticket.setBillingStrategy(billingStrategy);
                    break;
            }
        }

        double currentFee = ticket.calculateBaseAmount();
        continous1 = true;
        while (continous1) {
            System.out.println("Available Discount : ");
            System.out.println("1. Numbering Discount");
            System.out.println("Which kind of discount would you like to apply : ");
            //  System.out.println("2. Percentage Discount");
            String choice = scanner.nextLine();
            double discountAmount = 0.0;
            switch (choice) {
                case "1":
                    System.out.println("Enter discount amount:");
                    discountAmount = Double.parseDouble(scanner.nextLine());
                    ticket.setDiscountStrategy(new ParkingDiscount());
                    continous1 = false;
                    break;
                //case "2":
                //    System.out.println("Enter percentage discount (e.g., enter 10 for 10%):");
                //    double percentage = Double.parseDouble(scanner.nextLine());
                //    // Assuming we have a method to get the current parking fee
                //    double currentFee = 100.0; // Placeholder for current parking fee
                //    discountAmount = (percentage / 100) * currentFee;
                //    break;
                default:
                    System.out.println("Invalid choice.");
                    continous2 = true;
                    System.out.println("No active ticket found for this vehicle.");
                    System.out.println("=========================================================================================================");
                    while (continous2) {
                        System.out.print("Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ");
                        choice = scanner.nextLine();
                        switch (choice) {
                                case "1":
                                    continous2 = false;
                                    System.out.println("=========================================================================================================");
                                    break;
                                case "2":
                                    System.out.println("=========================================================================================================");
                                    return;   
                                default:
                                    System.out.println("=========================================================================================================");
                                    System.out.println("****************** Please enter 1 or 2 ******************");
                            }
                    }
            }

            double discountedAmount = ticket.applyDiscount(currentFee, discountAmount);
            System.out.println("Discounted amount: " + discountedAmount);
            System.out.println("=========================================================================================================");
        }
    }
    
}
