package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdViewVehicleRecordNotFound extends inputStreamSetUp {

    private CmdViewVehicleRecord cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdViewVehicleRecord();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    @ParameterizedTest
    @MethodSource("viewVehicleRecordNotFound")
    void shouldViewVehicleRecordNotFound(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("VIEW_VEHICLE_RECORD", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewVehicleRecordNotFound() {
        return Stream.of(
            Arguments.of(
                "INVALID_PLATE\n",
                new String[] {
                    "Enter vehicle license plate to view record:",
                    "Vehicle not found.",
                    "========================================================================================================="
                }
            )
        );
    }
}

