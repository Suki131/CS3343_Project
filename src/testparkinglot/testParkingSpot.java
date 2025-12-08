package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

public class testParkingSpot extends inputStreamSetUp {

    private Vehicle vehicle2;
    private DriverManager drivermanager;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        drivermanager = DriverManager.getInstance();
        vehicle2 = drivermanager.findVehicleByLicense("XY789");
    }
    
    @AfterEach
    void tearDown() {
    	var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }
    }

    @Test
    void assignVehicleNotNull() {
        ParkingSpot spot = new ParkingSpot("A6", ParkingSpotType.VAN_SPOT);
        assertTrue(spot.assignVehicle(vehicle2));
        assertTrue(spot.isOccupied());
        assertEquals(vehicle2, spot.getParkedVehicle());
        assertEquals("A6", spot.getSpotID());
        assertEquals(ParkingSpotType.VAN_SPOT, spot.getType());
        spot.removeVehicle();
    }

    @Test
    void assignVehicleNull() {
        ParkingSpot spot = new ParkingSpot("A7", ParkingSpotType.PRIVATE_SPOT);
        assertFalse(spot.assignVehicle(null));
        assertFalse(spot.isOccupied());
        assertNull(spot.getParkedVehicle());
        assertEquals("A7", spot.getSpotID());
        assertEquals(ParkingSpotType.PRIVATE_SPOT, spot.getType());
        spot.removeVehicle();
    }

    @Test
    void setOccupiedManually() {
        ParkingSpot spot = new ParkingSpot("A8", ParkingSpotType.TRUCK_3_5T_SPOT);
        spot.setOccupied(true);
        assertTrue(spot.isOccupied());
        spot.setOccupied(false);
        assertFalse(spot.isOccupied());
        assertEquals("A8", spot.getSpotID());
        assertEquals(ParkingSpotType.TRUCK_3_5T_SPOT, spot.getType());
        spot.removeVehicle();
    }

    @Test
    void removeVehicle() {
        ParkingSpot spot = new ParkingSpot("A9", ParkingSpotType.TRUCK_5_5T_SPOT);
        spot.assignVehicle(vehicle2);
        assertTrue(spot.isOccupied());
        spot.removeVehicle();
        assertFalse(spot.isOccupied());
        assertNull(spot.getParkedVehicle());
        assertEquals("A9", spot.getSpotID());
        assertEquals(ParkingSpotType.TRUCK_5_5T_SPOT, spot.getType());
        spot.removeVehicle();
    }
}
