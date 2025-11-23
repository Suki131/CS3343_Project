package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdCheckVacancyAllSpotTypes extends inputOctopusAlipayCredit {

    private CmdCheckVacancy cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdCheckVacancy();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    @ParameterizedTest
    @MethodSource("checkVacancyAllSpotTypes")
    void shouldDisplayAllSpotTypes(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        // Verify all spot types are displayed
        assertTrue(output.contains("Normal Spot") || output.contains("PRIVATE_SPOT"), 
            "Should display Normal Spot");
        assertTrue(output.contains("3.5T Truck Spot") || output.contains("TRUCK_3_5T_SPOT"), 
            "Should display 3.5T Truck Spot");
        assertTrue(output.contains("5.5T Truck Spot") || output.contains("TRUCK_5_5T_SPOT"), 
            "Should display 5.5T Truck Spot");
        assertTrue(output.contains("Van Spot") || output.contains("VAN_SPOT"), 
            "Should display Van Spot");

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> checkVacancyAllSpotTypes() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "========================================= Parking Lot Overview =========================================",
                    "Total Spots:",
                    "Occupied:",
                    "Available:",
                    "Spot Details:",
                    "========================================================================================================="
                }
            )
        );
    }
}

