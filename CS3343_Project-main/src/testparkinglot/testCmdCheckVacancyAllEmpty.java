package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdCheckVacancyAllEmpty extends inputOctopusAlipayCredit {

    private CmdCheckVacancy cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdCheckVacancy();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        
        // Clear all spots
        var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up: Clear all spots after test to avoid interfering with other tests
        var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("checkVacancyAllEmpty")
    void shouldDisplayAllEmptySpots(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
        
        // Verify all spots show as Available
        assertTrue(output.contains("Available"), "Should show Available spots");
        assertFalse(output.contains("Occupied -"), "Should not show any occupied spots");
    }

    static Stream<Arguments> checkVacancyAllEmpty() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "========================================= Parking Lot Overview =========================================",
                    "【Normal Spot】",
                    "Total Spots: 10",
                    "Occupied: 0",
                    "Available: 10",
                    "Available",
                    "========================================================================================================="
                }
            )
        );
    }
}

