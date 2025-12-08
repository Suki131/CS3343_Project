package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdApplyDiscount extends inputStreamSetUp {

    private CmdApplyDiscount cmd;
    private Staff staff;
    private Driver driver1;
    private Driver driver2;
    private Driver driver3;
    private Driver driver4;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Vehicle vehicle3;
    private Vehicle vehicle4;
    private Vehicle vehicle5;
    private ParkingSpot parkingspot1;;
    private ParkingSpot parkingspot2;
    private ParkingSpot parkingspot3;
    private ParkingSpot parkingspot4;
    private ParkingSpot parkingspot5;
    private Ticket ticket1;
    private Ticket ticket3;
    private Ticket ticket4;
    private Ticket ticket5;
    private TicketManager ticketmanager;
    private LocalDateTime expiryDate;
    private DriverManager drivermanager; 
    

    @BeforeEach
    void setUp() {
        super.setUpIO();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        
        // Clear all parking spots first to ensure clean state
        var allSpots = ParkingManager.getAllSpots();
        for (var spots : allSpots.values()) {
            for (ParkingSpot spot : spots) {
                spot.removeVehicle();
            }
        }
        
        // Clear TicketManager state
        ticketmanager = TicketManager.getInstance();
        // Note: TicketManager doesn't have a clear method, so we'll work with existing tickets
        
        drivermanager = DriverManager.getInstance();
        
        driver1 = drivermanager.retrieveDriverbyID("1234");
        driver2 = drivermanager.retrieveDriverbyID("1111");
        driver3 = drivermanager.retrieveDriverbyID("2222");
        driver4 = drivermanager.retrieveDriverbyID("3333");
        vehicle1 = drivermanager.findVehicleByLicense("AB123"); //NONE
        vehicle2 = drivermanager.findVehicleByLicense("XY789"); //NONE
        vehicle3 = drivermanager.findVehicleByLicense("CD123"); //DALIY
        vehicle4 = drivermanager.findVehicleByLicense("EF123"); //MONTHLY
        vehicle5 = drivermanager.findVehicleByLicense("JK789"); //ANNUALLY
        parkingspot1 = ParkingManager.convertVehicleTypeToSpot(vehicle1.getSpec());
        parkingspot1.assignVehicle(vehicle1);
        parkingspot2 = ParkingManager.convertVehicleTypeToSpot(vehicle2.getSpec());
        parkingspot2.assignVehicle(vehicle2);
        parkingspot3 = ParkingManager.convertVehicleTypeToSpot(vehicle3.getSpec());
        parkingspot3.assignVehicle(vehicle3);
        parkingspot4 = ParkingManager.convertVehicleTypeToSpot(vehicle4.getSpec());
        parkingspot4.assignVehicle(vehicle4);
        parkingspot5 = ParkingManager.convertVehicleTypeToSpot(vehicle5.getSpec());
        parkingspot5.assignVehicle(vehicle5);
        ticket1 = new Ticket(vehicle1, parkingspot1);
        ticket3 = new Ticket(vehicle3, parkingspot3);
        ticket4 = new Ticket(vehicle4, parkingspot4);
        ticket5 = new Ticket(vehicle5, parkingspot5);
        expiryDate = LocalDateTime.now().plusMonths(1);
        ticketmanager.addTicket(vehicle1, ticket1);
        ticketmanager.addTicket(vehicle3, ticket3);
        ticketmanager.addTicket(vehicle4, ticket4);
        ticketmanager.addTicket(vehicle5, ticket5);
        

        cmd = new CmdApplyDiscount();
    }

    @AfterEach
    void tearDown() {
        driver1.setMembershipType(MembershipType.NONE);
    	parkingspot1.removeVehicle();
    	parkingspot2.removeVehicle();
    	parkingspot3.removeVehicle();
    	parkingspot4.removeVehicle();
    	parkingspot5.removeVehicle();
    }

    @ParameterizedTest
    @MethodSource("ApplyDiscountInvalidVehiclePlateInvalidOptionExit")
    void ApplyDiscountInvalidVehiclePlateInvalidOptionExit(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipExpiryDate(null);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountInvalidVehiclePlateInvalidOptionExit() {
        return Stream.of(
            Arguments.of(
            	"AZ123\n" +
            	"3\n" +
            	"2\n",
                new String[]{
                    "Please enter vehicle license plate to apply discount:",
                    "Vehicle not found in the parking lot.",
                    "=========================================================================================================",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "****************** Please enter 1 or 2 ******************",
                    "=========================================================================================================",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                }
            )
        );
    }
    @ParameterizedTest
    @MethodSource("ApplyDiscountInvalidVehiclePlateReEnter")
    void ApplyDiscountInvalidVehiclePlateReEnter(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipExpiryDate(null);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountInvalidVehiclePlateReEnter() {
        return Stream.of(
            Arguments.of(
            	"AZ123\n" +
            	"1\n" +
                "AB123\n" +
                "1\n" +
                "10\n",
                new String[]{
                    "Please enter vehicle license plate to apply discount:",
                    "Vehicle not found in the parking lot.",
                    "=========================================================================================================",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "=========================================================================================================",
                    "Please enter vehicle license plate to apply discount:",
                    "Available Discount : ",
                    "1. Numbering Discount",
                    "Which kind of discount would you like to apply : ",
                    "Enter discount amount:",
                    "Discounted amount: 0",
                    "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountSuccessfully")
    void ApplyDiscountSuccessfully(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipExpiryDate(null);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountSuccessfully() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "1\n" +
                "10\n",
                new String[]{
                    "Please enter vehicle license plate to apply discount:",
                    "Available Discount : ",
                    "1. Numbering Discount",
                    "Which kind of discount would you like to apply : ",
                    "Enter discount amount:",
                    "Discounted amount: 0",
                    "========================================================================================================="
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("ApplyDiscountNoActiveReEntered")
    void ApplyDiscountNoActiveReEntered(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipExpiryDate(null);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountNoActiveReEntered() {
        return Stream.of(
            Arguments.of(
                "XY789\n" +
                "1\n" +
                "AB123\n" +
                "1\n" +
                "10\n",
                new String[]{
                    "Please enter vehicle license plate to apply discount:",
                    "No active ticket found for this vehicle.",
                    "=========================================================================================================",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "Please enter vehicle license plate to apply discount:",
                    "Available Discount : ",
                    "1. Numbering Discount",
                    "Which kind of discount would you like to apply : ",
                    "Enter discount amount:",
                    "Discounted amount: 0",
                    "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountNoActiveReEnterExit")
    void ApplyDiscountNoActiveReEnterExit(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountNoActiveReEnterExit() {
        return Stream.of(
            Arguments.of(
                "XY789\n" +
                "10\n" +
                "1\n" +
                "AZ123\n" +
                "2\n",
                new String[]{
                    "Please enter vehicle license plate to apply discount:",
                    "No active ticket found for this vehicle.",
                    "=========================================================================================================",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "Please enter vehicle license plate to apply discount:",
                    "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountNoneHourly")
    void ApplyDiscountNoneHourly(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipType(MembershipType.NONE);
        driver1.setMembershipExpiryDate(expiryDate);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected + "Output : " + output);
        }
    }
    
    static Stream<Arguments> ApplyDiscountNoneHourly() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: 0.0",
                        "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountDayDaily")
    void ApplyDiscountDayDaily(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        // CD123 belongs to driver2 (DAILY membership), so set driver2's expiry date
        driver2.setMembershipExpiryDate(expiryDate);
        driver2.setMembershipType(MembershipType.DAILY);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected + "Output : " + output);
        }
    }
    
    static Stream<Arguments> ApplyDiscountDayDaily() {
        return Stream.of(
            Arguments.of(
                "CD123\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: -10.0",
                        "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountMonthMonthly")
    void ApplyDiscountMonthMonthly(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipExpiryDate(expiryDate);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountMonthMonthly() {
        return Stream.of(
            Arguments.of(
                "EF123\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: -10.0",
                        "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountYearAnnually")
    void ApplyDiscountYearAnnually(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipExpiryDate(expiryDate);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountYearAnnually() {
        return Stream.of(
            Arguments.of(
                "JK789\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: -10.0",
                        "========================================================================================================="
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("ApplyDiscountMembershipNull")
    void ApplyDiscountMembershipNull(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipType(null);
        driver1.setMembershipExpiryDate(expiryDate);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountMembershipNull() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: 0.0",
                        "========================================================================================================="
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("ApplyDiscountMembershipDateNull")
    void ApplyDiscountMembershipDateNull(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipType(MembershipType.NONE);
        driver1.setMembershipExpiryDate(null);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountMembershipDateNull() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: 0.0",
                        "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountMembershipDateExpried")
    void ApplyDiscountMembershipDateExpried(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);
        driver1.setMembershipType(MembershipType.NONE);
        driver1.setMembershipExpiryDate(LocalDateTime.of(2025, 11, 1, 1, 1));

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountMembershipDateExpried() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "1\n" +
                "10\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: 0.0",
                        "========================================================================================================="
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("ApplyDiscountInvalidPlanReEnterValidOption")
    void ApplyDiscountInvalidPlanReEnterValidOption(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountInvalidPlanReEnterValidOption() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "2\n" +
                "1\n" +
                "1\n" +
                "10\n",
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Invalid choice.",
                        "=========================================================================================================",
                        "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                        "=========================================================================================================",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Enter discount amount:",
                        "Discounted amount: 0.0",
                        "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("ApplyDiscountInvalidExit")
    void ApplyDiscountInvalidExit(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountInvalidExit() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "2\n" +
                "2\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Invalid choice.",
                        "=========================================================================================================",
                        "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                        "=========================================================================================================",
                }
            )
        );
    }


    @ParameterizedTest
    @MethodSource("ApplyDiscountInvalidInvalidExit")
    void ApplyDiscountInvalidInvalidExit(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("APPLY_DISCOUNT", staff);

        String output = getOutput();
        
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }
    }
    
    static Stream<Arguments> ApplyDiscountInvalidInvalidExit() {
        return Stream.of(
            Arguments.of(
                "AB123\n" +
                "2\n" +
                "3\n" +
                "2\n" ,
                new String[]{
                		"Please enter vehicle license plate to apply discount:",
                        "Available Discount : ",
                        "1. Numbering Discount",
                        "Which kind of discount would you like to apply : ",
                        "Invalid choice.",
                        "=========================================================================================================",
                        "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                        "=========================================================================================================",
                        "****************** Please enter 1 or 2 ******************",
                        "========================================================================================================="
                }
            )
        );
    }
}