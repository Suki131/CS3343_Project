package testparkinglot;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.*;

import parkinglot.*;

public class testAnnuallyBilling {

    private AnnualBilling annually_billing;

    @BeforeEach
    void setUp() {
    	annually_billing = AnnualBilling.getInstance();
    	annually_billing.setFeeRate(0.0);
    }

    @Test
    @DisplayName("Singleton：get the same instance")
    void shouldReturnSameSingletonInstance() {
        assertSame(AnnualBilling.getInstance(), AnnualBilling.getInstance());
    }

    @Test
    @DisplayName("calculateBill：No rate set, 0 dollar will be charged for any number of days.")
    void shouldReturnZeroWhenRateIsZero() {
        Ticket ticket1 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE, new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 23, 55))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket1.setEntryTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        ticket1.setExitTime(LocalDateTime.of(2025, 12, 16, 23, 55));
        assertEquals(0.0, annually_billing.calculateBill(ticket1));

        Ticket ticket2 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 23, 55))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket2.setEntryTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        ticket2.setExitTime(LocalDateTime.of(2025, 11, 26, 23, 55));
        assertEquals(0.0, annually_billing.calculateBill(ticket2));
    }
    
    @Test
    @DisplayName("calculateBill : Parked less than 1 year")
    void shouldCharge0_testone() {
        
        Ticket ticket3 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 0, 0))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket3.setEntryTime(LocalDateTime.of(2025, 11, 17, 0, 0));
        ticket3.setExitTime(LocalDateTime.of(2025, 7, 27, 0, 0));

        assertEquals(0, annually_billing.calculateBill(ticket3));
    }
    
    @Test
    @DisplayName("calculateBill : Parked for 1 Year")
    void shouldCharge0_testtwo() {
    	annually_billing.setFeeRate(100.0);
        
        Ticket ticket3 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 0, 0))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket3.setEntryTime(LocalDateTime.of(2025, 11, 17, 0, 0));
        ticket3.setExitTime(LocalDateTime.of(2026, 12, 17, 0, 0));

        assertEquals(100.0, annually_billing.calculateBill(ticket3));
    }

    @Test
    @DisplayName("setFeeRate：Check whether YEAR_RATE changed")
    void setFeeRateShouldReturnTrueOnlyIfFeeMatchesCurrentRate() {
    	annually_billing.setFeeRate(150.0);

        assertEquals(150,annually_billing.getFeeRate());  // 一樣 → 冇改 → true
        assertNotSame(149,annually_billing.getFeeRate()); // 唔同 → 要改 → false
        assertNotSame(0.0,annually_billing.getFeeRate());   // 唔同
    }
}
