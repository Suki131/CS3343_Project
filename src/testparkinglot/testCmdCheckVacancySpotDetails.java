package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdCheckVacancySpotDetails extends inputStreamSetUp {

    private CmdCheckVacancy cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdCheckVacancy();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        

        var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("checkVacancySpotDetails")
    void shouldDisplaySpotDetails(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected + "\nActual: " + output);
        }
    }

    static Stream<Arguments> checkVacancySpotDetails() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "┌────────────────────────┬────────────────────────────────────┐",
                    "Spot ID",
                    "Status",
                    "├────────────────────────┼────────────────────────────────────┤",
                    "│",
                    "│",
                    "└────────────────────────┴────────────────────────────────────┘"
                }
            )
        );
    }
}

