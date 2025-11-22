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

public class testCmdViewVehicleRecordDifferentStatuses extends inputOctopusAlipayCredit {

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
    @MethodSource("viewVehicleRecordDifferentStatuses")
    void shouldViewVehicleRecordDifferentStatuses(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Create a vehicle with tickets in different statuses
        Driver driver = new Driver("TEST004", "Test Driver 4", "66666666", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("STATUS_TEST", VehicleType.PRIVATE, driver);
        driverManager.addDriver(driver);

        ParkingSpot spot = new ParkingSpot("PRIVATE_SPOT_4", ParkingSpotType.PRIVATE_SPOT);
        
        // Ticket with ENTERED status
        Ticket ticket1 = new Ticket(vehicle, spot);
        ticket1.setEntryTime(LocalDateTime.now().minusHours(1));
        ticket1.setExitTime(LocalDateTime.now().plusHours(1));
        ticket1.changeStatus(TicketStatus.ENTERED);
        ticket1.setParkingFee(0.0);
        
        // Ticket with PAID status
        Ticket ticket2 = new Ticket(vehicle, spot);
        ticket2.setEntryTime(LocalDateTime.now().minusDays(1));
        ticket2.setExitTime(LocalDateTime.now().minusDays(1).plusHours(2));
        ticket2.changeStatus(TicketStatus.PAID);
        ticket2.setParkingFee(20.0);
        
        // Ticket with EXITED status
        Ticket ticket3 = new Ticket(vehicle, spot);
        ticket3.setEntryTime(LocalDateTime.now().minusDays(2));
        ticket3.setExitTime(LocalDateTime.now().minusDays(2).plusHours(3));
        ticket3.changeStatus(TicketStatus.EXITED);
        ticket3.setParkingFee(30.0);
        
        ticketManager.addTicket(vehicle, ticket1);
        ticketManager.addTicket(vehicle, ticket2);
        ticketManager.addTicket(vehicle, ticket3);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordDifferentStatuses() {
        return Stream.of(
            Arguments.of(
                "STATUS_TEST\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: STATUS_TEST",
                    "Total Tickets: 3",
                    "ENTERED",
                    "PAID",
                    "EXITED",
                    "========================================================================================================="
                }
            )
        );
    }
}

