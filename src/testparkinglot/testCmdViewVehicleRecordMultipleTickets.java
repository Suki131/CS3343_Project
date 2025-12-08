package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdViewVehicleRecordMultipleTickets extends inputStreamSetUp {

    private CmdViewVehicleRecord cmd;
    private Staff staff;
    private DriverManager driverManager;
    private TicketManager ticketManager;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdViewVehicleRecord();
        staff = new Staff("STAFF001", "Test Staff");
        driverManager = DriverManager.getInstance();
        ticketManager = TicketManager.getInstance();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    @ParameterizedTest
    @MethodSource("viewVehicleRecordMultipleTickets")
    void shouldViewVehicleRecordMultipleTickets(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Create a vehicle with multiple tickets
        Driver driver = new Driver("TEST002", "Test Driver 2", "88888888", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("MULTI_TICKET", VehicleType.PRIVATE, driver);
        driverManager.addDriver(driver);

        ParkingSpot spot = new ParkingSpot("PRIVATE_SPOT_2", ParkingSpotType.PRIVATE_SPOT);
        
        // Add multiple tickets
        for (int i = 0; i < 3; i++) {
            Ticket ticket = new Ticket(vehicle, spot);
            ticket.setEntryTime(LocalDateTime.now().minusDays(i + 1));
            ticket.setExitTime(LocalDateTime.now().minusDays(i + 1).plusHours(1));
            ticket.changeStatus(TicketStatus.PAID);
            ticket.setParkingFee(10.0 * (i + 1));
            ticketManager.addTicket(vehicle, ticket);
        }

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordMultipleTickets() {
        return Stream.of(
            Arguments.of(
                "MULTI_TICKET\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: MULTI_TICKET",
                    "Total Tickets: 3",
                    "【Ticket 1/3】",
                    "【Ticket 2/3】",
                    "【Ticket 3/3】",
                    "========================================================================================================="
                }
            )
        );
    }
}

