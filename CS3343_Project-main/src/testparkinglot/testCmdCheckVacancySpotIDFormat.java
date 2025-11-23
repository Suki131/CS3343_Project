package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdCheckVacancySpotIDFormat extends inputOctopusAlipayCredit {

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
    @MethodSource("checkVacancySpotIDFormat")
    void shouldDisplayCorrectSpotIDFormat(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
        
        // Verify spot ID format (should be like PRIVATE_SPOT_1, VAN_SPOT_1, etc.)
        assertTrue(output.contains("PRIVATE_SPOT_") || output.contains("PRIVATE_SPOT"), 
            "Should contain PRIVATE_SPOT format");
        assertTrue(output.contains("VAN_SPOT_") || output.contains("VAN_SPOT"), 
            "Should contain VAN_SPOT format");
        assertTrue(output.contains("TRUCK_3_5T_SPOT_") || output.contains("TRUCK_3_5T_SPOT"), 
            "Should contain TRUCK_3_5T_SPOT format");
        assertTrue(output.contains("TRUCK_5_5T_SPOT_") || output.contains("TRUCK_5_5T_SPOT"), 
            "Should contain TRUCK_5_5T_SPOT format");
    }

    static Stream<Arguments> checkVacancySpotIDFormat() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "========================================= Parking Lot Overview =========================================",
                    "Spot Details:",
                    "│        Spot ID         │             Status                 │",
                    "========================================================================================================="
                }
            )
        );
    }
}

