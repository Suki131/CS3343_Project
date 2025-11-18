package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

public class testPaymentInvoker extends inputOctopusAlipayCredit {

    private PaymentInvoker invoker;
    private Ticket ticket1;

    @BeforeEach
    void setUp() {
        super.setUpIO(); 
        invoker = PaymentInvoker.getInstance();
        ticket1 = new Ticket(
            new Vehicle("AB123", VehicleType.PRIVATE,
                new Driver("Jacky", "12345678", MembershipType.DAILY,
                LocalDateTime.of(2026, 11, 16, 23, 55))),
            new ParkingSpot("001", ParkingSpotType.PRIVATE_SPOT)
        );
    }
    
    @AfterEach
    void tearDownIO() {
        super.tearDownIO();
        super.resetOutput();
    }
    
    public static Stream<Arguments> alipaySuccessCasesWithInput() {
        return Stream.of(
            Arguments.of(
                "PAY_BY_ALIPAY", 150.0, "12345678\n",  
                "Please enter your Alipay HK account phone number:",
                "Processing Alipay HK payment of amount: 150.0",
                "Payment of 150.0 via Alipay HK successful for account: 12345678",
                true
            ),
            Arguments.of(
                "PAY_BY_ALIPAY", 99.5, "98765432\n",
                "Please enter your Alipay HK account phone number:",
                "Processing Alipay HK payment of amount: 99.5",
                "Payment of 99.5 via Alipay HK successful for account: 98765432",
                true
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("alipaySuccessCasesWithInput")
    void shouldProcessAlipayPayment_direct(
            String cmd, double amount, String simulatedInput,
            String prompt, String processing, String successMsg,
            boolean expectedResult) {
    	 System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

         try (Scanner scanner = new Scanner(System.in)) {
	        // Act
	        boolean result = invoker.executeCommand(cmd, amount, ticket1, scanner);
	
	        // Assert
	        String output = getOutput();
	
	        assertTrue(output.contains(prompt));
	        assertTrue(output.contains(processing));
	        assertTrue(output.contains(successMsg));
	        assertEquals(expectedResult, result);			
         }
    }

    @Test
    void getSameInstance() {
        assertSame(invoker, PaymentInvoker.getInstance());
    }
}