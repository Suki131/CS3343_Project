package parkinglot;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class CmdParkingPlan implements StaffCommand {
    DriverManager driverManager = DriverManager.getInstance();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Scanner scanner = SmartParkingSystem.getScanner();

    @Override
    public void execute(String cmdName, Staff staff) {
        System.out.println("The following parking plans are available:");
        System.out.println("1. Daily Parking Plan");
        System.out.println("2. Monthly Parking Plan");
        System.out.println("3. Annual Parking Plan");
        System.out.println("4. Exit");
        System.out.println("Enter parking plan to purchase:");
        String planDetails = scanner.nextLine();
        String driverId = "";
        Driver driver = null;
        switch (planDetails) {
            case "1":
                System.out.println("Enter the driver's ID for the parking plan:");
                driverId = scanner.nextLine();
                driver = driverManager.retrieveDriverbyID(driverId);
                if (driver == null) {
                    System.out.println("************* Invalid driver ID *************");
                    System.out.println("=========================================================================================================");
                    return;
                }
                System.out.println("How many days of daily parking plan to purchase?");
                int days = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Purchasing Daily Parking Plan for " + days + " days.");
                System.out.println("The expiration date for this plan is " + LocalDateTime.now().plusDays(days).format(formatter) + ".");
                System.err.println("Confirm purchase? (yes/no)");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    driver.setMembershipType(MembershipType.DAILY);
                    driver.setMembershipExpiryDate(LocalDateTime.now().plusDays(days));
                } else {
                    System.out.println("************* Purchase cancelled *************");
                }
                break;
            case "2":
                System.out.println("Enter the driver's ID for the parking plan:");
                driverId = scanner.nextLine();
                driver = driverManager.retrieveDriverbyID(driverId);
                if (driver == null) {
                    System.out.println("************* Invalid driver ID *************");
                    System.out.println("=========================================================================================================");
                    return;
                }
                System.out.println("How many months of monthly parking plan to purchase?");
                int months = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Purchasing Monthly Parking Plan for " + months + " months.");
                System.out.println("The expiration date for this plan is " + LocalDateTime.now().plusMonths(months).format(formatter) + ".");
                System.err.println("Confirm purchase? (yes/no)");
                confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    driver.setMembershipType(MembershipType.MONTHLY);
                    driver.setMembershipExpiryDate(LocalDateTime.now().plusMonths(months));
                } else {
                    System.out.println("************* Purchase cancelled *************");
                }
                break;
            case "3":
                System.out.println("Enter the driver's ID for the parking plan:");
                driverId = scanner.nextLine();
                driver = driverManager.retrieveDriverbyID(driverId);
                if (driver == null) {
                    System.out.println("************* Invalid driver ID *************");
                    System.out.println("=========================================================================================================");
                    return;
                }
                System.out.println("How many years of annual parking plan to purchase?");
                int years = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Purchasing Annual Parking Plan for " + years + " years.");
                System.out.println("The expiration date for this plan is " + LocalDateTime.now().plusYears(years).format(formatter) + ".");
                System.err.println("Confirm purchase? (yes/no)");
                confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    driver.setMembershipType(MembershipType.ANNUALLY);
                    driver.setMembershipExpiryDate(LocalDateTime.now().plusYears(years));
                } else {
                    System.out.println("************* Purchase cancelled *************");
                }
                break;
            case "4":
                System.out.println("************* Exiting parking plan selection *************");
                System.out.println("=========================================================================================================");
                return;
            default:
                System.out.println("************* Invalid Option *************");
                System.out.println("=========================================================================================================");
                return;
        }

        System.out.println("************* Parking plan purchased successfully *************");
        System.out.println("=========================================================================================================");
    }

}
