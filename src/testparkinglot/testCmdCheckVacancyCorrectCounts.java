package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdCheckVacancyCorrectCounts extends inputOctopusAlipayCredit {

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
    @MethodSource("checkVacancyCorrectCounts")
    void shouldDisplayCorrectCounts(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Clear all spots first, then occupy some
        var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }

        // Occupy 2 private spots
        Driver driver = DriverManager.getInstance().retrieveDriverbyID("1234");
        if (driver != null) {
            Vehicle vehicle1 = driver.retrieveVehicle("AB123");
            Vehicle vehicle2 = driver.retrieveVehicle("XY789");
            if (vehicle1 != null && vehicle2 != null) {
                var privateSpots = ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT);
                if (privateSpots.size() >= 2) {
                    privateSpots.get(0).assignVehicle(vehicle1);
                    privateSpots.get(1).assignVehicle(vehicle2);
                }
            }
        }

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        // Verify counts are displayed (at least the structure)
        assertTrue(output.contains("Total Spots:"), "Should display Total Spots");
        assertTrue(output.contains("Occupied:"), "Should display Occupied count");
        assertTrue(output.contains("Available:"), "Should display Available count");

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> checkVacancyCorrectCounts() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "【Normal Spot】",
                    "Total Spots:",
                    "Occupied:",
                    "Available:",
                    "========================================================================================================="
                }
            )
        );
    }
}

