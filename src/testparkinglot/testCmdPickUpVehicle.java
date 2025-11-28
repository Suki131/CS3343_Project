package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdPickUpVehicle extends inputOctopusAlipayCredit {

    private CmdPickUpVehicle cmd;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        cmd = new CmdPickUpVehicle();
    }

    @Test
    public void driverNotFoundExit() {
        Scanner testScanner = new Scanner("ABC123\nNOID\nNOCONTACT\n2\n");
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", null);

        assertNull(DriverManager.getInstance().retrieveDriver("NOID","NOCONTACT"));
    }

    @Test
    public void driverNotFoundRetryThenExit() {
        Scanner testScanner = new Scanner("ABC123\nNOID\nNOCONTACT\n1\nABC123\nNOID\nNOCONTACT\n2\n");
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", null);

        assertNull(DriverManager.getInstance().retrieveDriver("NOID","NOCONTACT"));
    }

    @Test
    public void vehicleNotFound() {
        Driver d = new Driver("Alice","111",MembershipType.NONE, LocalDateTime.now().plusDays(1));
        DriverManager.getInstance().retrieveDriverbyID(d.getDriverID());

        Scanner testScanner = new Scanner("ABC123\n111\n111\n2\n");
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", null);

        assertNull(d.retrieveVehicle("ABC123"));
    }
    
    @Test
    public void spotNotFound() {
        Driver d = new Driver("Bob","222",MembershipType.NONE, LocalDateTime.now().plusDays(1));
        Vehicle v = new Vehicle("XYZ999", VehicleType.PRIVATE, d);
        d.addVehicle(v);
        DriverManager.getInstance().retrieveDriverbyID(d.getDriverID());

        Scanner testScanner = new Scanner("XYZ999\n222\n222\n2\n");
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", null);

        assertNull(ParkingManager.findParkingSpotByVehicle(v));
    }

    @Test
    public void spotFoundInvalidPayment() {
        Driver d = new Driver("Carl","333",MembershipType.NONE, LocalDateTime.now().plusDays(1));
        Vehicle v = new Vehicle("LMN123", VehicleType.PRIVATE, d);
        d.addVehicle(v);
        ParkingSpot spot = ParkingManager.getAvailableSpot(ParkingSpotType.PRIVATE_SPOT);
        spot.assignVehicle(v);
        DriverManager.getInstance().retrieveDriverbyID(d.getDriverID());

        Ticket t = new Ticket(v, spot);
        d.setCurrentTicket(t);

        Scanner testScanner = new Scanner("LMN123\n333\n333\n9\n2\n12345678\n");
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", null);

        assertEquals(TicketStatus.ENTERED, d.getCurrentTicket().getStatus());
    }

    @Test
    public void spotFoundValidPayment() {
    	Driver driver = new Driver("2445", "John Doe", "12345678", MembershipType.NONE, null, new ArrayList<>());
        Vehicle vehicle = new Vehicle("AD213", VehicleType.VAN, driver);
        DriverManager.getInstance().addDriver(driver);
        ParkingSpot spot = ParkingManager.convertVehicleTypeToSpot(vehicle.getSpec());
        spot.assignVehicle(vehicle);
        Driver driver2 = DriverManager.getInstance().retrieveDriver(driver.getDriverID(), driver.getContactInfo());
        assertNotNull(driver2);
        Ticket t = new Ticket(vehicle, spot);
        driver.setCurrentTicket(t);
        TicketManager.getInstance().addTicket(vehicle, t); // good practice

        // Use the actual random driver ID
        String realId = driver.getDriverID();
        
        

        Scanner testScanner = new Scanner(
            "AD213\n2445\n12345678\n1\n12345678\n3\n"
        );
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", driver);

        assertEquals(TicketStatus.EXITED, driver.getCurrentTicket().getStatus());
    }

    
    @Test
    public void spotFoundInvalidVehicle() {
    	Driver driver = new Driver("1254", "John Doe", "12345678", MembershipType.NONE, null, new ArrayList<>());
        Vehicle vehicle1 = new Vehicle("KA123", VehicleType.VAN, driver);
        Vehicle vehicle2 = new Vehicle("AS123", VehicleType.VAN, driver);
        DriverManager.getInstance().addDriver(driver);
        ParkingSpot spot1 = ParkingManager.convertVehicleTypeToSpot(vehicle1.getSpec());
        ParkingSpot spot2 = ParkingManager.convertVehicleTypeToSpot(vehicle2.getSpec());
        spot2.assignVehicle(vehicle2);
        Driver driver2 = DriverManager.getInstance().retrieveDriver(driver.getDriverID(), driver.getContactInfo());
        assertNotNull(driver2);
        Ticket t1 = new Ticket(vehicle1, spot1);
        Ticket t2 = new Ticket(vehicle2, spot2);
        driver.setCurrentTicket(t2);
        TicketManager.getInstance().addTicket(vehicle2, t2); // good practice

        // Use the actual random driver ID
        String realId = driver.getDriverID();
        
        

        Scanner testScanner = new Scanner(
            "KA123\n1254\n12345678\nAS123\n1254\n12345678\n1\n12345678\n3"
        );
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", driver);
        String output = getOutput();
        System.err.println(output);

        assertEquals(TicketStatus.EXITED, driver.getCurrentTicket().getStatus());
    }
    
    @Test
    public void vehicleNotFoundinvalid() {
    	Driver driver1 = new Driver("1233", "John Doe", "12345678", MembershipType.NONE, null, new ArrayList<>());
    	Driver driver2 = new Driver("1873", "John Doe", "12345678", MembershipType.NONE, null, new ArrayList<>());
        Vehicle vehicle1 = new Vehicle("AJ123", VehicleType.VAN, driver1);
        Vehicle vehicle2 = new Vehicle("AS123", VehicleType.VAN, driver1);
        Vehicle vehicle3 = new Vehicle("AI123", VehicleType.VAN, driver2);
        DriverManager.getInstance().addDriver(driver1);
        ParkingSpot spot1 = ParkingManager.convertVehicleTypeToSpot(vehicle1.getSpec());
        ParkingSpot spot2 = ParkingManager.convertVehicleTypeToSpot(vehicle2.getSpec());
        spot2.assignVehicle(vehicle2);
        
        Ticket t1 = new Ticket(vehicle1, spot1);
        Ticket t2 = new Ticket(vehicle2, spot2);
        driver1.setCurrentTicket(t2);
        TicketManager.getInstance().addTicket(vehicle2, t2); // good practice

        // Use the actual random driver ID
        String realId = driver1.getDriverID();
        
        

        Scanner testScanner = new Scanner(
            "AI123\n1254\n12345678\nAS123\n1254\n12345678\n1\n12345678\n3"
        );
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PICKUP", driver1);

        assertEquals(TicketStatus.ENTERED, driver1.getCurrentTicket().getStatus());
    }


}
