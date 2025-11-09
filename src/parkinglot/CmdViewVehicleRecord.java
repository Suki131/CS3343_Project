package src.parkinglot;
import java.util.Scanner;

public class CmdViewVehicleRecord implements StaffCommand {
    DriverManager driverManager = DriverManager.getInstance();
    TicketManager ticketManager = TicketManager.getInstance();

    @Override
    public void execute(String cmdName, Staff staff) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter vehicle license plate to view record:");
        String licensePlate = scanner.nextLine();
        Vehicle vehicle = driverManager.findVehicleByLicense(licensePlate);
        if (vehicle != null) {
            ticketManager.printVehicleTicketHistory(vehicle);
        } else {
            System.out.println("Vehicle not found.");
        }
        System.out.println("=========================================================================================================");
    }
}
