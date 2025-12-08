package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdViewDriverInfo extends inputStreamSetUp {

    private CmdViewDriverInfo cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdViewDriverInfo();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    @ParameterizedTest
    @MethodSource("viewDriverInfoValidDriver")
    void shouldViewDriverInfoValidDriver(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("VIEW_DRIVER_INFO", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected + "\nActual: " + output);
        }
    }

    static Stream<Arguments> viewDriverInfoValidDriver() {
        return Stream.of(
            Arguments.of(
                "1234\n",
                new String[] {
                    "Enter driver ID to view information:",
                    "=========================================================================================================",
                    "Driver ID: 1234",
                    "Driver Name: John Doe",
                    "Driver Contact Info: 12345678",
                    "Driver Membership Type: NONE",
                    "Driver Vehicle List:",
                    "1. AB123",
                    "2. XY789",
                    "========================================================================================================="
                }
            ),
            Arguments.of(
                "1111\n",
                new String[] {
                    "Enter driver ID to view information:",
                    "=========================================================================================================",
                    "Driver ID: 1111",
                    "Driver Name: Chris Wong",
                    "Driver Contact Info: 87654321",
                    "Driver Membership Type: DAILY",
                    "Driver Membership Expiry Date:",
                    "Driver Vehicle List:",
                    "1. CD123",
                    "2. ST789",
                    "========================================================================================================="
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("viewDriverInfoInvalidDriverID")
    void shouldViewDriverInfoInvalidDriverID(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("VIEW_DRIVER_INFO", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> viewDriverInfoInvalidDriverID() {
        return Stream.of(
            Arguments.of(
                "INVALID_ID\n",
                new String[] {
                    "Enter driver ID to view information:",
                    "Invalid driver ID.",
                    "========================================================================================================="
                }
            ),
            Arguments.of(
                "\n",
                new String[] {
                    "Enter driver ID to view information:",
                    "Invalid driver ID.",
                    "========================================================================================================="
                }
            )
        );
    }
}

