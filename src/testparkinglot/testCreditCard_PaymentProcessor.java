package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCreditCard_PaymentProcessor extends inputStreamSetUp {

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

    @AfterEach
    void tearDownIO() {
        super.tearDownIO();
        super.resetOutput();
    }

    public static Stream<Arguments> creditCardSuccessCases() {
        return Stream.of(
            Arguments.of("PAY_BY_CREDIT_CARD", 150.0,"8888888888888888\n123\n1111",
                "Please enter your Credit Card number:",
                "Please enter your Credit Card CVV:",
                "Please enter your Credit Card Expiry Date",
                "Payment of 150.0",
                "successful for card number: 8888888888888888",
                true),

            Arguments.of("PAY_BY_CREDIT_CARD", 99.4,"8888888888888888\n123\n1111",
                    "Please enter your Credit Card number:",
                    "Please enter your Credit Card CVV:",
                    "Please enter your Credit Card Expiry Date",
                    "Payment of 99.4",
                    "successful for card number: 8888888888888888",
                true)
        );
    }

    @ParameterizedTest
    @MethodSource("creditCardSuccessCases")
    void shouldProcessCreditCard_direct(
            String cmd, double amount, String simulatedInput,
            String prompt1, String prompt2, String prompt3, String processing, String successMsg,
            boolean expectedResult) {

		Scanner testScanner = new Scanner(simulatedInput);
		SmartParkingSystem.injectScannerForTest(testScanner);

            CreditCard_PaymentProcessor processor = new CreditCard_PaymentProcessor();
            boolean result = processor.processPayment(cmd, amount, ticket1);

            String output = getOutput();

            assertTrue(output.contains(prompt1), "Missing prompt");
            assertTrue(output.contains(prompt2), "Missing prompt");
            assertTrue(output.contains(prompt3), "Missing prompt");
            assertTrue(output.contains(processing), "Missing processing");
            assertTrue(output.contains(successMsg), "Missing success message");
            assertEquals(expectedResult, result, "Payment result mismatch");
            assertEquals(TicketStatus.PAID, ticket1.getStatus(), "Ticket not marked as PAID");
    }
}