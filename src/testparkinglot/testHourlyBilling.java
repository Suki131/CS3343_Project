package testparkinglot;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.*;

import parkinglot.*;

public class testHourlyBilling {
	private HourlyBilling hourly_billing;

    @BeforeEach
    void setUp() {
    	hourly_billing = HourlyBilling.getInstance();
    	hourly_billing.setFeeRate(5.0);
    }

    @AfterEach
    void tearDown() {
    	hourly_billing = HourlyBilling.getInstance();
    	hourly_billing.setFeeRate(0.0);
    }
    
    @Test
    @DisplayName("Singleton：get the same instance")
    void shouldReturnSameSingletonInstance() {
        assertSame(HourlyBilling.getInstance(), HourlyBilling.getInstance());
    }

    @Test
    @DisplayName("calculateBill : Parked less than 1 day")
    void shouldChargeLessThanOneDay() {
        
        Ticket ticket1 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jack", "12345678", MembershipType.NONE, LocalDateTime.of(2026, 11, 16, 00, 00))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket1.setEntryTime(LocalDateTime.of(2025, 11, 16, 00, 00));
        ticket1.setExitTime(LocalDateTime.of(2025, 11, 16, 03, 00));
        assertEquals(15.0, hourly_billing.calculateBill(ticket1));
    }
   
    @Test
    @DisplayName("calculateBill : Parked for 1 day")
    void shouldChargeManyDay() {
        Ticket ticket2 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jack", "12345678", MembershipType.NONE, LocalDateTime.of(2026, 11, 16, 0, 0))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket2.setEntryTime(LocalDateTime.of(2025, 11, 16, 00, 00));
        ticket2.setExitTime(LocalDateTime.of(2025, 11, 17, 0, 0));

        assertEquals(120.0, hourly_billing.calculateBill(ticket2));
    }

    @Test
    @DisplayName("setFeeRate：Check whether HOURLY_RATE changed")
    void setFeeRateShouldReturnTrueOnlyIfFeeMatchesCurrentRate() {
    	hourly_billing.setFeeRate(150.0);

        assertEquals(150,hourly_billing.getFeeRate());  // 一樣 → 冇改 → true
        assertNotSame(149,hourly_billing.getFeeRate()); // 唔同 → 要改 → false
        assertNotSame(0.0,hourly_billing.getFeeRate());   // 唔同
    }
}
