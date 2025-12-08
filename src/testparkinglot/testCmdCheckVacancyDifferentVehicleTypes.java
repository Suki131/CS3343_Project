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

public class testCmdCheckVacancyDifferentVehicleTypes extends inputStreamSetUp {

    private CmdCheckVacancy cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdCheckVacancy();
        staff = new Staff("STAFF001", "Test Staff");
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        
        // Clear all spots first
        var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }
        
        // Setup: Occupy spots with different vehicle types
        Driver driver1 = new Driver("TEST009", "Test Driver 9", "11111111", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Driver driver2 = new Driver("TEST010", "Test Driver 10", "10101010", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Driver driver3 = new Driver("TEST011", "Test Driver 11", "20202020", MembershipType.NONE, null, new ArrayList<Vehicle>());
        Driver driver4 = new Driver("TEST012", "Test Driver 12", "30303030", MembershipType.NONE, null, new ArrayList<Vehicle>());
        
        // Occupy one spot of each type
        var privateSpots = ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT);
        var vanSpots = ParkingManager.getSpotsByType(ParkingSpotType.VAN_SPOT);
        var truck35Spots = ParkingManager.getSpotsByType(ParkingSpotType.TRUCK_3_5T_SPOT);
        var truck55Spots = ParkingManager.getSpotsByType(ParkingSpotType.TRUCK_5_5T_SPOT);
        
        Vehicle v1 = new Vehicle("PRIVATE1", VehicleType.PRIVATE, driver1);
        privateSpots.get(0).assignVehicle(v1);
        Vehicle v2 = new Vehicle("VAN001", VehicleType.VAN, driver2);
        vanSpots.get(0).assignVehicle(v2);
        Vehicle v3 = new Vehicle("TRUCK35", VehicleType.TRUCK_3_5T, driver3);
        truck35Spots.get(0).assignVehicle(v3);
        Vehicle v4 = new Vehicle("TRUCK55", VehicleType.TRUCK_5_5T, driver4);
        truck55Spots.get(0).assignVehicle(v4);
    }

    @ParameterizedTest
    @MethodSource("checkVacancyDifferentVehicleTypes")
    void shouldDisplayDifferentVehicleTypes(String simulatedInput, String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("CHECK_VACANCY", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
        
        // Verify different vehicle types are shown
        assertTrue(output.contains("Occupied - PRIVATE1"));
        assertTrue(output.contains("Occupied - VAN001"));
        assertTrue(output.contains("Occupied - TRUCK35"));
        assertTrue(output.contains("Occupied - TRUCK55"));
    }

    static Stream<Arguments> checkVacancyDifferentVehicleTypes() {
        return Stream.of(
            Arguments.of(
                "",
                new String[] {
                    "========================================= Parking Lot Overview =========================================",
                    "【Normal Spot】",
                    "【Van Spot】",
                    "【3.5T Truck Spot】",
                    "【5.5T Truck Spot】",
                    "Occupied:",
                    "Available:",
                    "Occupied -",
                    "========================================================================================================="
                }
            )
        );
    }
}

