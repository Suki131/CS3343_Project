package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdMakePayment extends inputOctopusAlipayCredit {

    private CmdMakePayment cmd;
    private Driver driver;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        super.setUpIO();

        cmd = new CmdMakePayment();

        LocalDateTime entryTime = LocalDateTime.now().minusHours(2);
        driver = new Driver("Jacky", "12345678", MembershipType.NONE, null);
        Vehicle vehicle = new Vehicle("AB123", VehicleType.PRIVATE, driver);
        ParkingSpot spot = new ParkingSpot("001", ParkingSpotType.PRIVATE_SPOT);
        ticket = new Ticket(vehicle, spot);

        ticket.setEntryTime(entryTime);

        driver.setCurrentTicket(ticket);

        TicketManager.getInstance().addTicket(vehicle, ticket);
    }

    @ParameterizedTest
    @MethodSource("alipayFlow")
    void shouldCompletePaymentFlow_viaAlipay(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.NONE);
    	driver.setMembershipExpiryDate(null);

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

        assertEquals(TicketStatus.PAID, ticket.getStatus(), "Ticket should be PAID");
    }
    
    static Stream<Arguments> alipayFlow() {
        return Stream.of(
            Arguments.of(
                "1\n" +
                "12345678\n",
                20.0,
                new String[]{
                    "Calculating parking fee...",
                    "Your parking fee is: $20.0",
                    "Payment Method :",
                    "1. AliPay HK",
                    "Please enter your Alipay HK account phone number:",
                    "Processing Alipay HK payment of amount: 20.0",
                    "Payment of 20.0 via Alipay HK successful for account: 12345678"
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("octopusFlow")
    void shouldCompletePaymentFlow_viaOctopus(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.DAILY);
    	driver.setMembershipExpiryDate(LocalDateTime.of(2026, 11, 16, 23, 55));
    	
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

        // === 驗證 ticket 已付費 ===
        assertEquals(TicketStatus.PAID, ticket.getStatus(), "Ticket should be PAID");
    }
    
    public static Stream<Arguments> octopusFlow() {
    	return Stream.of(
                Arguments.of(
                    "2\n" +
                    "12345678\n",
                    0.0,
                    new String[] {
                        "Calculating parking fee...",
                        "Your parking fee is: $0.0",
                        "Payment Method :",
                        "2. Octopus Card",
		                "Please enter your Octopus Card number: ",
		                "Processing Octopus payment of amount: 0.0",
		                "Payment of 0.0 via Octopus successful for card number: 12345678",
                    }
                )
            );
    }
    
    @ParameterizedTest
    @MethodSource("creditCardFlow")
    void shouldCompletePaymentFlow_viaCreditCard(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.MONTHLY);
    	driver.setMembershipExpiryDate(LocalDateTime.of(2026, 11, 16, 23, 55));
    	
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

        assertEquals(TicketStatus.PAID, ticket.getStatus(), "Ticket should be PAID");
    }
    

    public static Stream<Arguments> creditCardFlow() {
        return Stream.of(
            Arguments.of(
                "3\n8888888888888888\n888\n88/88\n",
                0.0,
                new String[] {
                    "Calculating parking fee...",
                    "Your parking fee is: $0.0",
                    "Payment Method :",
                    "3. Credit Card",
                    "Please enter your Credit Card number:",
                    "Please enter your Credit Card CVV:",
                    "Please enter your Credit Card Expiry Date",
                    "Payment of ",
                    "successful for card number: 8888888888888888"
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidFlow")
    void invalidPaymentMethod(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.ANNUALLY);
    	driver.setMembershipExpiryDate(LocalDateTime.of(2026, 11, 16, 23, 55));

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

        assertEquals(TicketStatus.ENTERED, ticket.getStatus(), "Ticket should be ENTERED, as user choose invalid payment method");
    }
    
    public static Stream<Arguments> invalidFlow() {
    	return Stream.of(
                Arguments.of(
                    "4\n",
                    0.0,
                    new String[] {
                        "Calculating parking fee...",
                        "Your parking fee is: $0.0",
                        "Payment Method :",
		                "Invalid payment method selected."
                    }
                )
            );
    }
    
    @ParameterizedTest
    @MethodSource("nullMembershipDateNotNull")
    void shouldnullMembershipNotNullDate(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(null);
    	driver.setMembershipExpiryDate(LocalDateTime.of(2026, 11, 16, 23, 55));

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> nullMembershipDateNotNull() {
        return Stream.of(
            Arguments.of(
                "1\n" +
                "12345678\n",
                20.0,
                new String[]{
                		"Calculating parking fee...",
                        "Your parking fee is: $20.0",
                        "Payment Method :"
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("NotNullMembershipDateNull")
    void shouldNotMembershipNullDate(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.ANNUALLY);
    	driver.setMembershipExpiryDate(null);

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> NotNullMembershipDateNull() {
        return Stream.of(
            Arguments.of(
                "1\n" +
                "12345678\n",
                20.0,
                new String[]{
                		"Calculating parking fee...",
                        "Your parking fee is: $20.0",
                        "Payment Method :"
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("NotNullMembershipDateExpiried")
    void shouldNotMembershipDateExpiried(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.ANNUALLY);
    	driver.setMembershipExpiryDate(LocalDateTime.of(2025, 11, 16, 23, 55));

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> NotNullMembershipDateExpiried() {
        return Stream.of(
            Arguments.of(
                "1\n" +
                "12345678\n",
                20.0,
                new String[]{
                		"Calculating parking fee...",
                        "Your parking fee is: $20.0",
                        "Payment Method :"
                }
            )
        );
    }
    


    @ParameterizedTest
    @MethodSource("hourlyMembershipNotNullDate")
    void shouldHourlyMembershipNotNullDate(
            String simulatedInput,
            double expectedFee,
            String[] expectedOutputs) {
    	driver.setMembershipType(MembershipType.NONE);
    	driver.setMembershipExpiryDate(LocalDateTime.of(2026, 11, 16, 23, 55));
    	
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        cmd.execute("MAKE_PAYMENT", driver);

        String output = getOutput();

        assertTrue(output.contains(String.format("Your parking fee is: $%.1f", expectedFee)),
                "Expected fee: " + expectedFee);

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> hourlyMembershipNotNullDate() {
        return Stream.of(
            Arguments.of(
                "1\n" +
                "12345678\n",
                20.0,
                new String[]{
                		"Calculating parking fee...",
                        "Your parking fee is: $20.0",
                        "Payment Method :"
                }
            )
        );
    }
    
}