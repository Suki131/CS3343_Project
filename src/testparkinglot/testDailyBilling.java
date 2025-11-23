package testparkinglot;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import parkinglot.*;
import java.time.LocalDateTime;

class testDailyBilling {

    private DailyBilling daily_billing;

    @BeforeEach
    void setUp() {
    	daily_billing = DailyBilling.getInstance();
    	daily_billing.setFeeRate(0.0);
    }
    
    @AfterEach
    void tearDown() {
    	daily_billing = DailyBilling.getInstance();
    	daily_billing.setFeeRate(0.0);
    }
    
    @Test
    @DisplayName("Singleton：get the same instance")
    void shouldReturnSameSingletonInstance() {
        assertSame(DailyBilling.getInstance(), DailyBilling.getInstance());
    }

    @Test
    @DisplayName("calculateBill：No rate set, 0 dollar will be charged for any number of days.")
    void shouldReturnZeroWhenRateIsZero() {
        Ticket ticket1 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE, new Driver("Jacky", "12345678", MembershipType.DAILY, LocalDateTime.of(2026, 11, 16, 23, 55))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket1.setEntryTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        ticket1.setExitTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        assertEquals(0.0, daily_billing.calculateBill(ticket1));

        Ticket ticket2 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.DAILY, LocalDateTime.of(2026, 11, 16, 23, 55))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket2.setEntryTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        assertEquals(0.0, daily_billing.calculateBill(ticket2));
    }
    
    @Test
    @DisplayName("calculateBill : Parked for 1 day")
    void shouldChargeOneDayForPartialDay() {
    	daily_billing.setFeeRate(100.0);
        
        Ticket ticket3 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.DAILY, LocalDateTime.of(2025, 11, 16, 0, 0))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket3.setExitTime(LocalDateTime.of(2025, 11, 17, 0, 0));

        assertEquals(100.0, daily_billing.calculateBill(ticket3));
    }

    @Test
    @DisplayName("setFeeRate：Check whether DAILT_RATE changed")
    void setFeeRateShouldReturnTrueOnlyIfFeeMatchesCurrentRate() {
    	daily_billing.setFeeRate(150.0);

        assertEquals(150,daily_billing.getFeeRate());  // 一樣 → 冇改 → true
        assertNotSame(149,daily_billing.getFeeRate()); // 唔同 → 要改 → false
        assertNotSame(0.0,daily_billing.getFeeRate());   // 唔同
    }

}