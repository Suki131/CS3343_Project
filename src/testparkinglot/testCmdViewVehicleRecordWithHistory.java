package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdViewVehicleRecordWithHistory extends inputStreamSetUp {

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
    @MethodSource("viewVehicleRecordWithHistory")
    void shouldViewVehicleRecordWithHistory(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Create a driver and vehicle with ticket history
        Driver driver = driverManager.retrieveDriverbyID("1234");
        Vehicle vehicle = driver.retrieveVehicle("AB123");
        ParkingSpot spot = new ParkingSpot("PRIVATE_SPOT_1", ParkingSpotType.PRIVATE_SPOT);
        Ticket ticket1 = new Ticket(vehicle, spot);
        ticket1.setEntryTime(LocalDateTime.now().minusDays(5));
        ticket1.setExitTime(LocalDateTime.now().minusDays(5).plusHours(2));
        ticket1.changeStatus(TicketStatus.PAID);
        ticket1.setParkingFee(20.0);
        
        Ticket ticket2 = new Ticket(vehicle, spot);
        ticket2.setEntryTime(LocalDateTime.now().minusDays(2));
        ticket2.setExitTime(LocalDateTime.now().minusDays(2).plusHours(3));
        ticket2.changeStatus(TicketStatus.PAID);
        ticket2.setParkingFee(30.0);
        
        ticketManager.addTicket(vehicle, ticket1);
        ticketManager.addTicket(vehicle, ticket2);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordWithHistory() {
        return Stream.of(
            Arguments.of(
                "AB123\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: AB123",
                    "Total Tickets:",
                    "Ticket",
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

