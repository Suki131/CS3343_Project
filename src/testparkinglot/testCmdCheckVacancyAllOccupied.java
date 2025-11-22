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

public class testCmdCheckVacancyAllOccupied extends inputOctopusAlipayCredit {

    private CmdCheckVacancy cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdCheckVacancy();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        
        // Setup: Create drivers and vehicles to occupy all spots of one type
        Driver driver = new Driver("TEST008", "Test Driver 8", "22222222", MembershipType.NONE, null, new ArrayList<Vehicle>());
        var privateSpots = ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT);
        
        // Occupy all private spots
        for (int i = 0; i < privateSpots.size(); i++) {
            Vehicle vehicle = new Vehicle("CAR" + String.format("%03d", i), VehicleType.PRIVATE, driver);
            privateSpots.get(i).assignVehicle(vehicle);
        }
    }

    @ParameterizedTest
    @MethodSource("checkVacancyAllOccupied")
    void shouldDisplayAllOccupiedSpots(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
        
        // Verify all private spots are occupied
        var privateSpots = ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT);
        assertTrue(output.contains("Occupied: " + privateSpots.size()), 
            "Should show all spots as occupied");
        assertTrue(output.contains("Available: 0"), 
            "Should show 0 available spots");
    }

    static Stream<Arguments> checkVacancyAllOccupied() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "========================================= Parking Lot Overview =========================================",
                    "【Normal Spot】",
                    "Total Spots: 10",
                    "Occupied: 10",
                    "Available: 0",
                    "Occupied -",
                    "========================================================================================================="
                }
            )
        );
    }
}

