package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdViewVehicleRecordNoHistory extends inputOctopusAlipayCredit {

    private CmdViewVehicleRecord cmd;
    private Staff staff;
    private DriverManager driverManager;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdViewVehicleRecord();
        staff = new Staff("STAFF001", "Test Staff");
        driverManager = DriverManager.getInstance();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    @ParameterizedTest
    @MethodSource("viewVehicleRecordNoHistory")
    void shouldViewVehicleRecordNoHistory(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Create a vehicle without ticket history
        Driver driver = new Driver("TEST001", "Test Driver", "99999999", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Vehicle vehicle = new Vehicle("NO_HISTORY", VehicleType.PRIVATE, driver);
        driverManager.addDriver(driver);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordNoHistory() {
        return Stream.of(
            Arguments.of(
                "NO_HISTORY\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "No ticket history found for vehicle: NO_HISTORY",
                    "========================================================================================================="
                }
            )
        );
    }
}

