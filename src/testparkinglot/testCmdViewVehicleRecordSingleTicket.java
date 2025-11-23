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

public class testCmdViewVehicleRecordSingleTicket extends inputOctopusAlipayCredit {

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
    @MethodSource("viewVehicleRecordSingleTicket")
    void shouldViewVehicleRecordSingleTicket(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Create a vehicle with single ticket
        Driver driver = new Driver("TEST003", "Test Driver 3", "77777777", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("SINGLE_TICKET", VehicleType.PRIVATE, driver);
        driverManager.addDriver(driver);

        ParkingSpot spot = new ParkingSpot("PRIVATE_SPOT_3", ParkingSpotType.PRIVATE_SPOT);
        Ticket ticket = new Ticket(vehicle, spot);
        ticket.setEntryTime(LocalDateTime.now().minusDays(1));
        ticket.setExitTime(LocalDateTime.now().minusDays(1).plusHours(2));
        ticket.changeStatus(TicketStatus.PAID);
        ticket.setParkingFee(25.0);
        ticketManager.addTicket(vehicle, ticket);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordSingleTicket() {
        return Stream.of(
            Arguments.of(
                "SINGLE_TICKET\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: SINGLE_TICKET",
                    "Total Tickets: 1",
                    "【Ticket 1/1】",
                    "Ticket ID",
                    "Entry Time",
                    "Exit Time",
                    "Ticket Status",
                    "Total Amount",
                    "========================================================================================================="
                }
            )
        );
    }
}

