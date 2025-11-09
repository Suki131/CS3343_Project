package src.parkinglot;
import java.util.Scanner;

public class CmdRegisterVehicle implements DriverCommand {

    @Override
    public void execute(String cmdName, Driver driver) {
        Scanner scanner = new Scanner(System.in);
        Vehicle vehicle = null;
        ParkingSpot spot = null;
        Ticket ticket = null;
        System.out.println("Enter vehicle license plate : ");
        String licensePlate = scanner.nextLine();
        while (driver.retrieveVehicle(licensePlate) == null) {
            System.out.println("Vehicle Type : ");
            System.out.println(" 1. Private Car\n 2. Van\n 3. Truck 3.5 Ton\n 4. Truck 5.5 Ton");
            System.out.print("Enter vehicle type (1-4) : ");
            String vehicleType = scanner.nextLine();
            switch (vehicleType.toLowerCase()) {
                case "1":
                    vehicle = new Vehicle(licensePlate, VehicleType.PRIVATE, driver);
                    break;
                case "2":
                    vehicle = new Vehicle(licensePlate, VehicleType.VAN, driver);
                    break;
                case "3":
                    vehicle = new Vehicle(licensePlate, VehicleType.TRUCK_3_5T, driver);
                    break;
                case "4":
                    vehicle = new Vehicle(licensePlate, VehicleType.TRUCK_5_5T, driver);
                    break;
                default:
                    System.out.println("Invalid vehicle type.");
                    System.out.println("=========================================================================================================");
            }
        }
        vehicle = driver.retrieveVehicle(licensePlate);
        System.out.println("Vehicle registered successfully!");

        spot = ParkingManager.convertVehicleTypeToSpot(vehicle.getSpec());
        spot.assignVehicle(vehicle);
        ticket = new Ticket(vehicle, spot);
        driver.setCurrentTicket(ticket);
        TicketManager.getInstance().addTicket(vehicle, ticket);

        System.out.println("Vehicle " + licensePlate + " parked at spot " + spot.getSpotID());
        System.out.println("=========================================================================================================");
    }
}
