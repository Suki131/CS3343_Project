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

public class testCmdViewVehicleRecordDifferentVehicleTypes extends inputOctopusAlipayCredit {

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
    @MethodSource("viewVehicleRecordVanType")
    void shouldViewVehicleRecordVanType(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        Driver driver = new Driver("TEST005", "Test Driver 5", "55555555", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("VAN123", VehicleType.VAN, driver);
        driverManager.addDriver(driver);

        ParkingSpot spot = new ParkingSpot("VAN_SPOT_1", ParkingSpotType.VAN_SPOT);
        Ticket ticket = new Ticket(vehicle, spot);
        ticket.setEntryTime(LocalDateTime.now().minusDays(1));
        ticket.setExitTime(LocalDateTime.now().minusDays(1).plusHours(2));
        ticket.changeStatus(TicketStatus.PAID);
        ticket.setParkingFee(30.0);
        ticketManager.addTicket(vehicle, ticket);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordVanType() {
        return Stream.of(
            Arguments.of(
                "VAN123\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: VAN123",
                    "Total Tickets: 1",
                    "========================================================================================================="
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("viewVehicleRecordTruck35TType")
    void shouldViewVehicleRecordTruck35TType(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        Driver driver = new Driver("TEST006", "Test Driver 6", "44444444", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("TRUCK35", VehicleType.TRUCK_3_5T, driver);
        driverManager.addDriver(driver);

        ParkingSpot spot = new ParkingSpot("TRUCK_3_5T_SPOT_1", ParkingSpotType.TRUCK_3_5T_SPOT);
        Ticket ticket = new Ticket(vehicle, spot);
        ticket.setEntryTime(LocalDateTime.now().minusDays(1));
        ticket.setExitTime(LocalDateTime.now().minusDays(1).plusHours(2));
        ticket.changeStatus(TicketStatus.PAID);
        ticket.setParkingFee(40.0);
        ticketManager.addTicket(vehicle, ticket);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordTruck35TType() {
        return Stream.of(
            Arguments.of(
                "TRUCK35\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: TRUCK35",
                    "Total Tickets: 1",
                    "========================================================================================================="
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("viewVehicleRecordTruck55TType")
    void shouldViewVehicleRecordTruck55TType(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        Driver driver = new Driver("TEST007", "Test Driver 7", "33333333", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("TRUCK55", VehicleType.TRUCK_5_5T, driver);
        driverManager.addDriver(driver);

        ParkingSpot spot = new ParkingSpot("TRUCK_5_5T_SPOT_1", ParkingSpotType.TRUCK_5_5T_SPOT);
        Ticket ticket = new Ticket(vehicle, spot);
        ticket.setEntryTime(LocalDateTime.now().minusDays(1));
        ticket.setExitTime(LocalDateTime.now().minusDays(1).plusHours(2));
        ticket.changeStatus(TicketStatus.PAID);
        ticket.setParkingFee(50.0);
        ticketManager.addTicket(vehicle, ticket);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordTruck55TType() {
        return Stream.of(
            Arguments.of(
                "TRUCK55\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "=============== Vehicle Ticket History ===============",
                    "License Plate: TRUCK55",
                    "Total Tickets: 1",
                    "========================================================================================================="
                }
            )
        );
    }
}

