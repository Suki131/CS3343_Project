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

    @Test
    void testGetDriverID() {
        assertNotNull(driver.getDriverID());
    }

    @Test
    void testGetDriverIDWithConstructor() {
        List<Vehicle> vehicleList = new ArrayList<>();
        Driver newDriver = new Driver("TEST123", "Test", "12345678", MembershipType.NONE, LocalDateTime.now(), vehicleList);
        assertEquals("TEST123", newDriver.getDriverID());
    }

    @Test
    void testGetVehicleLicenseListEmpty() {
        Driver emptyDriver = new Driver("Jane Doe", "87654321", MembershipType.NONE, LocalDateTime.now().plusDays(30));
        emptyDriver.getVehicleLicenseList();
        assertTrue(emptyDriver.getVehicleList().isEmpty());
    }

    @Test
    void testGetVehicleLicenseListWithVehicles() {
        driver.getVehicleLicenseList();
        assertEquals(2, driver.getVehicleList().size());
    }

    @Test
    void testCurrentTicketIsNullByDefault() {
        Driver newDriver = new Driver("Jane Doe", "87654321", MembershipType.NONE, LocalDateTime.now().plusDays(30));
        assertNull(newDriver.getCurrentTicket());
    }

    @Test
    void testClearCurrentTicket() {
        ParkingSpot spot = new ParkingSpot("001", ParkingSpotType.PRIVATE_SPOT);
        Ticket ticket = new Ticket(vehicle1, spot);
        driver.setCurrentTicket(ticket);
        assertNotNull(driver.getCurrentTicket());
        
        driver.setCurrentTicket(null);
        assertNull(driver.getCurrentTicket());
    }

    @Test
    void testRetrieveVehicleFromMultipleVehicles() {
        driver.removeVehicle(vehicle2);
        Vehicle retrieved = driver.retrieveVehicle("AB123");
        assertNotNull(retrieved);
        assertEquals("AB123", retrieved.getLicensePlate());
    }

    @Test
    void testRemoveVehicleOneByOne() {
        assertEquals(2, driver.getVehicleList().size());
        driver.removeVehicle(vehicle1);
        assertEquals(1, driver.getVehicleList().size());
        driver.removeVehicle(vehicle2);
        assertEquals(0, driver.getVehicleList().size());
    }

    @Test
    void testAddMultipleVehicles() {
        Driver newDriver = new Driver("New Driver", "11111111", MembershipType.NONE, LocalDateTime.now().plusDays(30));
        assertEquals(0, newDriver.getVehicleList().size());
        
        newDriver.addVehicle(vehicle1);
        assertEquals(1, newDriver.getVehicleList().size());
        
        newDriver.addVehicle(vehicle2);
        assertEquals(2, newDriver.getVehicleList().size());
    }

    @Test
    void testSetMultipleMemberships() {
        driver.setMembershipType(MembershipType.DAILY);
        assertEquals(MembershipType.DAILY, driver.getMembershipType());
        
        driver.setMembershipType(MembershipType.MONTHLY);
        assertEquals(MembershipType.MONTHLY, driver.getMembershipType());
        
        driver.setMembershipType(MembershipType.ANNUALLY);
        assertEquals(MembershipType.ANNUALLY, driver.getMembershipType());
    }

    @Test
    void testSetMultipleContactInfo() {
        driver.setContactInfo("11111111");
        assertEquals("11111111", driver.getContactInfo());
        
        driver.setContactInfo("22222222");
        assertEquals("22222222", driver.getContactInfo());
    }

    @Test
    void testSetMultipleNames() {
        driver.setName("Alice");
        assertEquals("Alice", driver.getName());
        
        driver.setName("Bob");
        assertEquals("Bob", driver.getName());
    }

    @Test
    void testSetMultipleMembershipExpiryDates() {
        LocalDateTime date1 = LocalDateTime.now().plusDays(30);
        LocalDateTime date2 = LocalDateTime.now().plusDays(60);
        
        driver.setMembershipExpiryDate(date1);
        assertEquals(date1, driver.getMembershipExpiryDate());
        
        driver.setMembershipExpiryDate(date2);
        assertEquals(date2, driver.getMembershipExpiryDate());
    }

    

    @Test
    void testRetrieveNonExistentVehicleFromEmptyList() {
        Driver emptyDriver = new Driver("Empty Driver", "99999999", MembershipType.NONE, LocalDateTime.now().plusDays(30));
        Vehicle retrieved = emptyDriver.retrieveVehicle("NONEXISTENT");
        assertNull(retrieved);
    }

    @Test
    void testConstructorInitializesAllFields() {
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(45);
        Driver testDriver = new Driver("Test Name", "12345678", MembershipType.MONTHLY, expiryDate);
        
        assertEquals("Test Name", testDriver.getName());
        assertEquals("12345678", testDriver.getContactInfo());
        assertEquals(MembershipType.MONTHLY, testDriver.getMembershipType());
        assertEquals(expiryDate, testDriver.getMembershipExpiryDate());
        assertNull(testDriver.getCurrentTicket());
        assertTrue(testDriver.getVehicleList().isEmpty());
    }
}
