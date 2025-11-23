package testparkinglot;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.*;

import parkinglot.*;

public class testMonthlyBilling {

    private MonthlyBilling monthly_billing;

    @BeforeEach
    void setUp() {
    	monthly_billing = MonthlyBilling.getInstance();
    	monthly_billing.setFeeRate(0.0);
    }
    
    @AfterEach
    void tearDown() {
    	monthly_billing = MonthlyBilling.getInstance();
    	monthly_billing.setFeeRate(0.0);
    }

    @Test
    @DisplayName("Singleton：get the same instance")
    void shouldReturnSameSingletonInstance() {
        assertSame(MonthlyBilling.getInstance(), MonthlyBilling.getInstance());
    }

    @Test
    @DisplayName("calculateBill：No rate set, 0 dollar will be charged for any number of days.")
    void shouldReturnZeroWhenRateIsZero() {
        Ticket ticket1 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE, new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 23, 55))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket1.setEntryTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        ticket1.setExitTime(LocalDateTime.of(2025, 12, 16, 23, 55));
        assertEquals(0.0, monthly_billing.calculateBill(ticket1));

        Ticket ticket2 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 23, 55))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket2.setEntryTime(LocalDateTime.of(2025, 11, 16, 23, 55));
        ticket2.setExitTime(LocalDateTime.of(2025, 11, 26, 23, 55));
        assertEquals(0.0, monthly_billing.calculateBill(ticket2));
    }
    
    @Test
    @DisplayName("calculateBill : Parked less than 1 Month")
    void shouldCharge0_testone() {
        
        Ticket ticket3 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 0, 0))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket3.setEntryTime(LocalDateTime.of(2025, 11, 17, 0, 0));
        ticket3.setExitTime(LocalDateTime.of(2025, 11, 27, 0, 0));

        assertEquals(0, monthly_billing.calculateBill(ticket3));
    }
    
    @Test
    @DisplayName("calculateBill : Parked for 1 Month")
    void shouldCharge0_testtwo() {
    	monthly_billing.setFeeRate(100.0);
        
        Ticket ticket3 = new Ticket(new Vehicle("AB123",VehicleType.PRIVATE,new Driver("Jacky", "12345678", MembershipType.MONTHLY, LocalDateTime.of(2026, 11, 16, 0, 0))), new ParkingSpot("001",ParkingSpotType.PRIVATE_SPOT));
        ticket3.setEntryTime(LocalDateTime.of(2025, 11, 17, 0, 0));
        ticket3.setExitTime(LocalDateTime.of(2025, 12, 17, 0, 0));

        assertEquals(100.0, monthly_billing.calculateBill(ticket3));
    }

    @Test
    @DisplayName("setFeeRate：Check whether MONTHLY_RATE changed")
    void setFeeRateShouldReturnTrueOnlyIfFeeMatchesCurrentRate() {
    	monthly_billing.setFeeRate(150.0);

        assertEquals(150,monthly_billing.getFeeRate()); 
        assertNotSame(149,monthly_billing.getFeeRate());
        assertNotSame(0.0,monthly_billing.getFeeRate());
    }

}
