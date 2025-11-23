package parkinglot;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    private String driverID;
    private String name;
    private String contactInfo;
    private MembershipType membershipType;
    private LocalDateTime membershipExpiryDate;
    private ArrayList<Vehicle> vehicleList;
    private Ticket currentTicket;
    private final DriverInvoker driverInvoker = DriverInvoker.getInstance();

    public Driver(String name, String contactInfo, MembershipType membershipType, LocalDateTime membershipExpiryDate) {
        this.driverID = RandomIDGenerator.generateID(4);
        this.name = name;
        this.contactInfo = contactInfo;
        this.membershipType = membershipType;
        this.membershipExpiryDate = membershipExpiryDate;
        this.currentTicket = null;
        this.vehicleList = new ArrayList<>();
    }

    public Driver(String id, String name, String contactInfo, MembershipType membershipType, LocalDateTime membershipExpiryDate, List<Vehicle> vehicleList) {
        this.driverID = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.membershipType = membershipType;
        this.membershipExpiryDate = membershipExpiryDate;
        this.vehicleList = new ArrayList<>(vehicleList);
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public String getDriverID() {
        return driverID;
    }
    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public LocalDateTime getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    public void setMembershipExpiryDate(LocalDateTime membershipExpiryDate) {
        this.membershipExpiryDate = membershipExpiryDate;
    }

    public Vehicle retrieveVehicle(String licensePlate) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                return vehicle;
            }
        }
        return null;
    }

    public void getVehicleLicenseList() {
        if (vehicleList.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
	        for (Vehicle vehicle : vehicleList) {
	            System.out.println(vehicle.getLicensePlate());
	        }
        }
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }
    
    public void addVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }
    
    public void removeVehicle(Vehicle vehicle) {
        vehicleList.remove(vehicle);
    }

    public Ticket getCurrentTicket() {
        return currentTicket;
    }

    public void setCurrentTicket(Ticket ticket) {
        this.currentTicket = ticket;
    }

    public void executeCommand(String cmdName) {
        driverInvoker.executeCommand(cmdName, this);
    }
}
