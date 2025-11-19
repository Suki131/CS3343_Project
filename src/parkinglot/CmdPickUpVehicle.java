package src.parkinglot;
import java.util.Scanner;

public class CmdPickUpVehicle implements DriverCommand {

    @Override
    public void execute(String cmdName, Driver driver) {
        DriverManager driverManager = DriverManager.getInstance();
        DriverInvoker driverCommand = DriverInvoker.getInstance();
        boolean continous1 = true;
        while (continous1) { 
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter vehicle license plate to pick up:");
            String licensePlate = scanner.nextLine();
            System.out.println("Please enter Driver ID to proceed.");
            String id = scanner.nextLine();
            System.out.println("Please enter contact info to proceed.");
            String contactInfo = scanner.nextLine();
            driver = driverManager.retrieveDriver(id, contactInfo);
            
            if (driver == null) {
                System.out.println("=========================================================================================================");
                System.out.println("************ Driver not found. Cannot proceed with vehicle pickup ************");
                boolean continous2 = true;
                while (continous2) {
                    System.out.print("Action :\n1. Re-enter vehicle info\n2. Exit\nChoose your action : ");
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
                            System.out.println("****************** Please enter 1 or 2 ******************");
                    }
                }
            }
            if (driver != null) {
                Vehicle vehicle = driver.retrieveVehicle(licensePlate);
                if (vehicle != null) {
                    ParkingSpot spot = ParkingManager.findParkingSpotByVehicle(vehicle);
                    if (spot != null) {
                        spot.removeVehicle();
                        System.out.println("Vehicle " + licensePlate + " has been picked up from spot " + spot.getSpotID());
                        System.out.println("=========================================================================================================");
                        driverCommand.executeCommand("MAKE_PAYMENT", driver);
                        Ticket ticket = driver.getCurrentTicket();
                        if (ticket.getStatus() == TicketStatus.PAID) {
                            ticket.changeStatus(TicketStatus.EXITED);
                        }
                        continous1 = false;
                        System.out.println("=========================================================================================================");
                    } else {
                        System.out.println("************ Vehicle not found in any parking spot ************");
                    }
                } else {
                    System.out.println("************ Vehicle not found ************");
                }
            }
        }
    }
    
}
