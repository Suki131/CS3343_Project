package parkinglot;
import java.util.Scanner;
public class CmdApplyDiscount implements StaffCommand {

    @Override
    public void execute(String cmdName, Staff staff) {
        DiscountStrategy discountStrategy = null;
        boolean continous1 = true;
        Vehicle vehicle = null;
        Driver driver = null;
        boolean continous2 = true;
    	  Scanner scanner = SmartParkingSystem.getScanner();
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
        continous1 = true;
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
                                    System.out.println("=========================================================================================================");
                                    System.out.println("Please enter vehicle license plate to apply discount:");
                                    String rePlate = scanner.nextLine();
                                    vehicle = ParkingManager.findVehicle(rePlate);
                                    driver = vehicle != null ? vehicle.getOwnerDriver() : null;
                                    ticket = (vehicle != null) ? TicketManager.getInstance().getEnteredTicket(vehicle) : null;
                                    continous2 = false;
                                    continous1 = false;
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

        if (ticket == null) {
            System.out.println("No ticket available after prompts, exiting.");
            return;
        }

        BillingStrategy billingStrategy = HourlyBilling.getInstance();
        ticket.setBillingStrategy(billingStrategy);
        MembershipType mt = driver.getMembershipType();
        if (mt != null && driver.getMembershipExpiryDate() != null && driver.getMembershipExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            if (mt == MembershipType.NONE) {
                billingStrategy = HourlyBilling.getInstance();
                ticket.setBillingStrategy(billingStrategy);
            } else if (mt == MembershipType.DAILY) {
                billingStrategy = DailyBilling.getInstance();
                ticket.setBillingStrategy(billingStrategy);
            } else if (mt == MembershipType.MONTHLY) {
                billingStrategy = MonthlyBilling.getInstance();
                ticket.setBillingStrategy(billingStrategy);
            } else {
                billingStrategy = AnnualBilling.getInstance();
                ticket.setBillingStrategy(billingStrategy);
            }
        }

        double currentFee = ticket.calculateBaseAmount();
        continous1 = true;
        while (continous1) {
            System.out.println("Available Discount : ");
            System.out.println("1. Numbering Discount");
            System.out.println("Which kind of discount would you like to apply : ");
            String choice = scanner.nextLine();
            double discountAmount = 0.0;
            switch (choice) {
                case "1":
                    System.out.println("Enter discount amount:");
                    discountAmount = Double.parseDouble(scanner.nextLine());
                    ticket.setDiscountStrategy(new ParkingDiscount());
                    continous1 = false;
                    break;
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
