package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parkinglot.Driver;
import parkinglot.MembershipType;
import parkinglot.ParkingSpot;
import parkinglot.ParkingSpotType;
import parkinglot.Ticket;
import parkinglot.Vehicle;
import parkinglot.VehicleType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testDriver {

    private Driver driver;
    private Vehicle vehicle1;
    private Vehicle vehicle2;

    @BeforeEach
    void setUp() {
        driver = new Driver("John Doe", "john@example.com", 
                MembershipType.NONE, LocalDateTime.now().plusDays(30));
        vehicle1 = new Vehicle("ABC123", VehicleType.PRIVATE, driver);
        vehicle2 = new Vehicle("EFG123", VehicleType.VAN, driver);
    }

    @Test
    void testAddAndRetrieveVehicle() {
        Vehicle retrieved = driver.retrieveVehicle("ABC123");
        assertNotNull(retrieved);
        assertEquals("ABC123", retrieved.getLicensePlate());

        assertEquals(2, driver.getVehicleList().size());
    }

    @Test
    void testRetrieveVehicleNotFound() {
        driver.addVehicle(vehicle1);
        Vehicle retrieved = driver.retrieveVehicle("NOTFOUND");
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
        driver.setContactInfo("newemail@example.com");
        assertEquals("newemail@example.com", driver.getContactInfo());
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

        Driver anotherDriver = new Driver("D001", "Alice", "alice@mail.com",
                MembershipType.DAILY, LocalDateTime.now().plusDays(15), vehicleList);

        assertEquals(2, anotherDriver.getVehicleList().size());
        assertEquals("Alice", anotherDriver.getName());
    }
}
