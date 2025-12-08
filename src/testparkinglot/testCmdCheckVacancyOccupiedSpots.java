package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdCheckVacancyOccupiedSpots extends inputStreamSetUp {

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
    @MethodSource("checkVacancyWithOccupiedSpots")
    void shouldDisplayOccupiedSpots(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        // Setup: Create and occupy some spots
        Driver driver = DriverManager.getInstance().retrieveDriverbyID("1234");
        Vehicle vehicle = driver.retrieveVehicle("AB123");

        ParkingSpot spot = ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT).get(0);
        spot.assignVehicle(vehicle);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> checkVacancyWithOccupiedSpots() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "========================================= Parking Lot Overview =========================================",
                    "【Normal Spot】",
                    "Total Spots:",
                    "Occupied:",
                    "Available:",
                    "Occupied -",
                    "Available",
                    "========================================================================================================="
                }
            )
        );
    }
}

