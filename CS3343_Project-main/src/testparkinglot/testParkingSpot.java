package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.*;


import static org.junit.jupiter.api.Assertions.*;

public class testParkingSpot extends inputOctopusAlipayCredit {

    private Vehicle vehicle2;
    private DriverManager drivermanager; 
    

    @BeforeEach
    void setUp() {
        super.setUpIO();
        drivermanager = DriverManager.getInstance();
        vehicle2 = drivermanager.findVehicleByLicense("XY789");
    }
    
    @Test
    void assignVehicleNotNull() {
    	ParkingSpot spot = new ParkingSpot("A6", ParkingSpotType.VAN_SPOT);
    	assertTrue(spot. assignVehicle(vehicle2)) ;
    	assertTrue (spot. isOccupied());
    	assertEquals(vehicle2, spot.getParkedVehicle());
    }
    
}