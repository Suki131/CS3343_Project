package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parkinglot.Driver;
import parkinglot.DriverManager;
import parkinglot.MembershipType;
import parkinglot.ParkingSpot;
import parkinglot.ParkingSpotType;
import parkinglot.SmartParkingSystem;
import parkinglot.Ticket;
import parkinglot.Vehicle;
import parkinglot.VehicleType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class testDriver {

    private Driver driver;
    private Vehicle vehicle1;
    private Vehicle vehicle2;

    @BeforeEach
    void setUp() {
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        driver = new Driver("John Doe", "12345678", 
                MembershipType.NONE, LocalDateTime.now().plusDays(30));
        vehicle1 = new Vehicle("AB123", VehicleType.PRIVATE, driver);
        vehicle2 = new Vehicle("XY789", VehicleType.VAN, driver);
    }
    
    
    @Test 
    void testSetName(){
    	driver.setName("Jacky");
    	assertEquals(driver.getName(), "Jacky");
    }

    @Test
    void testAddAndRetrieveVehicle() {
        Vehicle retrieved = driver.retrieveVehicle("AB123");
        assertNotNull(retrieved);
        assertEquals("AB123", retrieved.getLicensePlate());

        assertEquals(2, driver.getVehicleList().size());
    }

    @Test
    void testRetrieveVehicleNotFound() {
        driver.addVehicle(vehicle1);
        Vehicle retrieved = driver.retrieveVehicle("NULL");
        assertNull(retrieved);
    }

    @Test
    void testRemoveVehicle() {
        driver.removeVehicle(vehicle1);
        driver.removeVehicle(vehicle2);
        assertTrue(driver.getVehicleList().isEmpty());
    }

    @Test
    void testSetAndGetContactInfo() {
        driver.setContactInfo("87654321");
        assertEquals("87654321", driver.getContactInfo());
    }

    @Test
    void testSetAndGetMembershipType() {
        driver.setMembershipType(MembershipType.ANNUALLY);
        assertEquals(MembershipType.ANNUALLY, driver.getMembershipType());
    }

    @Test
    void testSetAndGetMembershipExpiryDate() {
        LocalDateTime newDate = LocalDateTime.now().plusDays(60);
        driver.setMembershipExpiryDate(newDate);
        assertEquals(newDate, driver.getMembershipExpiryDate());
    }

    @Test
    void testSetAndGetTicket() {
    	ParkingSpot spot = new ParkingSpot("001", ParkingSpotType.PRIVATE_SPOT);
        Ticket ticket = new Ticket(vehicle1, spot);
        driver.setCurrentTicket(ticket);
        assertEquals(ticket, driver.getCurrentTicket());
    }

    @Test
    void testConstructorWithVehicleList() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);
        
        Driver anotherDriver = new Driver("1111", "Chris Wong", "87654321", MembershipType.DAILY, LocalDateTime.now().plusDays(10), vehicleList);

        assertEquals(2, anotherDriver.getVehicleList().size());
        assertEquals("Chris Wong", anotherDriver.getName());
    }
}
