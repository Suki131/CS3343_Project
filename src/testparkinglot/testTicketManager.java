package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.*;


import java.time.format.DateTimeFormatter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class testTicketManager {
    private Driver driver;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Ticket ticket1;
    private Ticket ticket2;
    private ParkingSpot parkingspot;
    private TicketManager ticketmanager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
	
	@BeforeEach
    void setUp() {
        driver = new Driver("Jacky", "12345678", 
                MembershipType.NONE, LocalDateTime.now().plusDays(30));
        vehicle1 = new Vehicle("AB123", VehicleType.PRIVATE, driver);
        vehicle2 = new Vehicle("EF123", VehicleType.VAN, driver);
        parkingspot = new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT);
        ticket1 = new Ticket(vehicle1, parkingspot);
        ticket2 = new Ticket(vehicle2, parkingspot);
        ticket2.changeStatus(TicketStatus.EXITED);
        ticketmanager = TicketManager.getInstance();
        entryTime = LocalDateTime.of(2025, 11, 16, 00, 00);
        exitTime = LocalDateTime.of(2025, 11, 17, 00, 00);
        System.setOut(new PrintStream(outContent));  
    }
	
	@Test
    void testgetInstance() {
        assertEquals(ticketmanager, TicketManager.getInstance());
    }
	
	@Test
    void testEnteredTlcketTicketStatusNotENTERED() {
		ticketmanager.addTicket(vehicle2, ticket2);
        Ticket ticket = ticketmanager.getEnteredTicket(vehicle2);
        assertNull(ticket);
    }
	
	@Test
    void testEnteredTlcketNoTicket() {
        Ticket ticket = ticketmanager.getEnteredTicket(vehicle1);
        assertNull(ticket);
    }
	
	@Test
    void testAddTicket() {
		ticketmanager.addTicket(vehicle1, ticket1);
        Ticket ticket = ticketmanager.getEnteredTicket(vehicle1);
        assertNotNull(ticket);
        assertEquals(vehicle1, ticket.getVehicle());

        assertEquals(ticket1,ticket);
    }
	
	@Test
    void testAddTicketVehicleNull() {
		ticketmanager.addTicket(null, ticket1);
    }
	
	@Test
    void testAddTicketTicketNull() {
		ticketmanager.addTicket(vehicle1, null);
    }
	
	@Test
    void testAddTicketBothNull() {
		ticketmanager.addTicket(null, null);
    }
	
	@Test
	void testprintVehicleTicketHistoryVehicleNull() {
		ticketmanager.printVehicleTicketHistory(null);
		String output = outContent.toString();

        assertTrue(output.contains("invalid vehicle."));
	}
	
	@Test
	void testprintVehicleTicketHistoryNull() {
		ticketmanager.printVehicleTicketHistory(vehicle1); 
		String output = outContent.toString();

        assertTrue(output.contains("No ticket history found for vehicle: AB123"));
	}
	
	@Test
	void testprintVehicleTicketHistory() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ticket1.setEntryTime(entryTime);
		ticket1.setExitTime(exitTime);
		ticketmanager.addTicket(vehicle1, ticket1);
		ticketmanager.printVehicleTicketHistory(vehicle1);
		
		String output = outContent.toString();

        
		assertTrue(output.contains("=============== Vehicle Ticket History ==============="));
		assertTrue(output.contains("License Plate: AB123"));
		assertTrue(output.contains("Total Tickets: 1"));
		assertTrue(output.contains("【Ticket 1/1】"));
		assertTrue(output.contains("┌────────────────────────────────┬────────────────────────────────────────────────┐"));
		assertTrue(output.contains("│ Ticket ID                      │ " + String.format("%-46s", ticket1.getTicketID()) + " │"));
		assertTrue(output.contains("├────────────────────────────────┼────────────────────────────────────────────────┤"));
		assertTrue(output.contains("│ Entry Time                     │ " + String.format("%-46s", ticket1.getEntryTime().format(formatter)) + " │"));
		assertTrue(output.contains("│ Exit Time                      │ " + String.format("%-46s", ticket1.getExitTime().format(formatter)) + " │"));
		assertTrue(output.contains("│ Ticket Status                  │ " + String.format("%-46s", ticket1.getStatus()) + " │"));
		assertTrue(output.contains("│ Total Amount                   │ " + String.format("%-46.2f", ticket1.getTotalAmount()) + " │"));
		assertTrue(output.contains("└────────────────────────────────┴────────────────────────────────────────────────┘"));
	}
}
