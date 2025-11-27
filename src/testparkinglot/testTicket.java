package testparkinglot;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parkinglot.*;

public class testTicket {

    private Vehicle vehicle;
    private ParkingSpot spot;
    private Driver driver;

    @BeforeEach
    void setUp() throws Exception {
        driver = new Driver("John", "12345678", MembershipType.MONTHLY, LocalDateTime.now().plusMonths(1));
        vehicle = new Vehicle("AB123", VehicleType.PRIVATE, driver);
        spot = new ParkingSpot("S1", ParkingSpotType.PRIVATE_SPOT);
    }

    @Test
    void testChangeStatusAndSetTimesAndGetters() {
        Ticket ticket = new Ticket(vehicle, spot);


        assertTrue(ticket.changeStatus(TicketStatus.ENTERED));
        
        assertFalse(ticket.changeStatus(TicketStatus.EXITED));
        assertEquals(TicketStatus.EXITED, ticket.getStatus());


        LocalDateTime oldEntry = ticket.getEntryTime();
        assertTrue(ticket.setEntryTime(oldEntry));

        LocalDateTime newEntry = LocalDateTime.now().minusHours(1);
        assertFalse(ticket.setEntryTime(newEntry));
        assertEquals(newEntry, ticket.getEntryTime());


        LocalDateTime oldExit = ticket.getExitTime();
        assertTrue(ticket.setExitTime(oldExit));

        LocalDateTime newExit = LocalDateTime.now().plusHours(2);
        assertFalse(ticket.setExitTime(newExit));
        assertEquals(newExit, ticket.getExitTime());


        assertEquals(vehicle, ticket.getVehicle());
        assertEquals(driver.getMembershipType(), ticket.getMembershipType());
        assertNotNull(ticket.getTicketID());
    }

    @Test
    void testApplyDiscountWithNullStrategy() {
        Ticket ticket = new Ticket(vehicle, spot);
        assertEquals(0, ticket.applyDiscount(100, 10));
        assertEquals(0, ticket.calDiscount(10));
    }

    @Test
    void testApplyDiscountWithRealStrategy() {
        Ticket ticket = new Ticket(vehicle, spot);
        ticket.setDiscountStrategy(new ParkingDiscount());
        assertEquals(90, ticket.applyDiscount(100, 10));
        assertEquals(-10, ticket.calDiscount(10));
    }

    @Test
    void testCalculateBaseAmountWithRealBillingStrategy() {
        Ticket ticket = new Ticket(vehicle, spot);
        ticket.setBillingStrategy(HourlyBilling.getInstance());
        ticket.setEntryTime(LocalDateTime.now().minusHours(2));
        ticket.setExitTime(LocalDateTime.now());

        double base = ticket.calculateBaseAmount();
        assertEquals(10.0, base);
        assertEquals(10.0, ticket.getBaseAmount());

        ticket.setParkingFee(80.0);
        assertEquals(80.0, ticket.getTotalAmount());
    }
}
