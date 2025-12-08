package testparkinglot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.*;

import java.time.LocalDateTime;

class testParkingManager {

    @BeforeEach
    void setUp() {
        ParkingManager.initializeSpots(2);
    }
    
    @AfterEach
    void tearDown() {
        ParkingManager.initializeSpots(10);
    }

    @Test
    void testAddRemoveBranches() {

        int before = ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT).size();
        String newId = ParkingManager.addParkingSpot(ParkingSpotType.PRIVATE_SPOT);
        assertTrue(newId.startsWith("PRIVATE_SPOT_"));
        assertEquals(before + 1, ParkingManager.getSpotsByType(ParkingSpotType.PRIVATE_SPOT).size());

        String removedId = ParkingManager.removeParkingSpot(ParkingSpotType.PRIVATE_SPOT);
        assertNotNull(removedId);

        ParkingManager.getSpotsByType(ParkingSpotType.VAN_SPOT).clear();
        assertNull(ParkingManager.removeParkingSpot(ParkingSpotType.VAN_SPOT));

        ParkingManager.getAllSpots().remove(ParkingSpotType.VAN_SPOT);
        assertNotNull(ParkingManager.addParkingSpot(ParkingSpotType.VAN_SPOT));
    }

    @Test
    void testFindVehicleAndFindParkingSpotBranches() {
        Driver d = new Driver("Alice", "111", MembershipType.MONTHLY, LocalDateTime.now().plusMonths(1));

        assertNull(ParkingManager.findParkingSpotByVehicle(null));

        Vehicle vMatch = new Vehicle("MATCH123", VehicleType.PRIVATE, d);
        ParkingSpot spotMatch = ParkingManager.getAvailableSpot(ParkingSpotType.PRIVATE_SPOT);
        spotMatch.assignVehicle(vMatch);
        assertEquals(vMatch, ParkingManager.findVehicle("MATCH123"));
        assertEquals(spotMatch, ParkingManager.findParkingSpotByVehicle(vMatch));

        Vehicle vOther = new Vehicle("OTHER999", VehicleType.PRIVATE, d);
        ParkingSpot spotOther = ParkingManager.getAvailableSpot(ParkingSpotType.PRIVATE_SPOT);
        spotOther.assignVehicle(vOther);
        Vehicle vMismatch = new Vehicle("DIFFERENT999", VehicleType.PRIVATE, d);
        assertNull(ParkingManager.findParkingSpotByVehicle(vMismatch));

        assertNull(ParkingManager.findVehicle("NO_SUCH_PLATE"));
        Vehicle vNotParked = new Vehicle("NEW000", VehicleType.VAN, d);
        assertNull(ParkingManager.findParkingSpotByVehicle(vNotParked));
    }

    @Test
    void testGetAvailableSpotBranches() {
        assertNotNull(ParkingManager.getAvailableSpot(ParkingSpotType.TRUCK_3_5T_SPOT));

        for (ParkingSpot s : ParkingManager.getSpotsByType(ParkingSpotType.TRUCK_5_5T_SPOT)) {
            s.setOccupied(true);
        }
        ParkingSpot newSpot = ParkingManager.getAvailableSpot(ParkingSpotType.TRUCK_5_5T_SPOT);
        assertNotNull(newSpot);

        ParkingManager.getAllSpots().remove(ParkingSpotType.VAN_SPOT);
        assertNotNull(ParkingManager.getAvailableSpot(ParkingSpotType.VAN_SPOT));
    }

    @Test
    void testConvertVehicleTypeBranches() {
        assertNotNull(ParkingManager.convertVehicleTypeToSpot(VehicleType.PRIVATE));
        assertNotNull(ParkingManager.convertVehicleTypeToSpot(VehicleType.TRUCK_3_5T));

        assertNull(ParkingManager.convertVehicleTypeToSpot(null));

        ParkingManager.getAllSpots().remove(ParkingSpotType.VAN_SPOT); 
        ParkingManager.removeParkingSpot(ParkingSpotType.VAN_SPOT); 
        ParkingManager.removeVehicleMapping(VehicleType.VAN);
        assertNull(ParkingManager.convertVehicleTypeToSpot(VehicleType.VAN));

        ParkingManager.mapVehicleType(VehicleType.VAN, ParkingSpotType.VAN_SPOT);
    }

    @Test
    void testDisplayParkingStatusBranches() {
        Driver d = new Driver("Carl", "333", MembershipType.DAILY, LocalDateTime.now().plusDays(1));
        Vehicle v = new Vehicle("OCC123", VehicleType.PRIVATE, d);
        ParkingSpot spot = ParkingManager.getAvailableSpot(ParkingSpotType.PRIVATE_SPOT);
        spot.assignVehicle(v);

        ParkingManager.displayParkingStatus();

        ParkingManager.getAllSpots().remove(ParkingSpotType.VAN_SPOT);
        ParkingManager.displayParkingStatus();
    }
}




