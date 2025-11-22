package parkinglot;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CmdViewDriverInfo implements StaffCommand {
    DriverManager driverManager = DriverManager.getInstance();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public void execute(String cmdName, Staff staff) {
    	Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("Enter driver ID to view information:");
        String driverId = scanner.nextLine();
        Driver driver = driverManager.retrieveDriverbyID(driverId);
        if (driver == null) {
            System.out.println("Invalid driver ID.");
            System.out.println("=========================================================================================================");
            return;
        }
        System.out.println("=========================================================================================================");
        System.out.println("Driver ID: " + driver.getDriverID());
        System.out.println("Driver Name: " + driver.getName());
        System.out.println("Driver Contact Info: " + driver.getContactInfo());
        System.out.println("Driver Membership Type: " + driver.getMembershipType());
        if (driver.getMembershipExpiryDate() != null) {
            System.out.println("Driver Membership Expiry Date: " + driver.getMembershipExpiryDate().format(formatter));
        }
        System.out.println("Driver Vehicle List: ");
        int i = 1;
        for (Vehicle vehicle : driver.getVehicleList()) {
            System.out.println(i + ". " + vehicle.getLicensePlate());
            i += 1;
        }
        System.out.println("=========================================================================================================");
    }
}
