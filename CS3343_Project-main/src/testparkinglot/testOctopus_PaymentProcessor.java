package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testOctopus_PaymentProcessor extends inputOctopusAlipayCredit {

    private Ticket ticket1;

    @BeforeEach
    void setUpIO() {
        super.setUpIO();
        ticket1 = new Ticket(
            new Vehicle("AB123", VehicleType.PRIVATE,
                new Driver("Jacky", "12345678", MembershipType.DAILY,
                    LocalDateTime.of(2026, 11, 16, 23, 55))),
            new ParkingSpot("001", ParkingSpotType.PRIVATE_SPOT)
        );
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    public static Stream<Arguments> octopusSuccessCases() {
        return Stream.of(
            Arguments.of("PAY_BY_OCTOPUS", 150.0, "12345678",
                "Please enter your Octopus Card number: ",
                "Processing Octopus payment of amount: 150.0",
                "Payment of 150.0 via Octopus successful for card number: 12345678",
                true),

            Arguments.of("PAY_BY_OCTOPUS", 99.5, "12345678",
                "Please enter your Octopus Card number: ",
                "Processing Octopus payment of amount: 99.5",
                "Payment of 99.5 via Octopus successful for card number: 12345678",
                true)
        );
    }

    @ParameterizedTest
    @MethodSource("octopusSuccessCases")
    void shouldProcessOctopusPayment_direct(
            String cmd, double amount, String simulatedInput,
            String prompt, String processing, String successMsg,
            boolean expectedResult) {

    		Scanner testScanner = new Scanner(simulatedInput);
    		SmartParkingSystem.injectScannerForTest(testScanner);

            Octopus_PaymentProcessor processor = new Octopus_PaymentProcessor();
            boolean result = processor.processPayment(cmd, amount, ticket1);

            String output = getOutput();

            assertTrue(output.contains(prompt), "Missing prompt");
            assertTrue(output.contains(processing), "Missing processing");
            assertTrue(output.contains(successMsg), "Missing success message");
            assertEquals(expectedResult, result, "Payment result mismatch");
            assertEquals(TicketStatus.PAID, ticket1.getStatus(), "Ticket not marked as PAID");
       
    }
}