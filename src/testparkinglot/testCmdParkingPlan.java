package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdParkingPlan extends inputStreamSetUp {

    private CmdParkingPlan cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        staff = new Staff("111", "Jane Doe");
        cmd = new CmdParkingPlan();
    }

    @ParameterizedTest
    @MethodSource("parkingPlanrTwoDay")
    void parkingPlanrTwoDay(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> parkingPlanrTwoDay() {
        return Stream.of(
            Arguments.of(
            	"1\n" +
            	"1234\n" +
            	"2\n" +
            	"yes\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "How many days of daily parking plan to purchase?",
                    "Purchasing Daily Parking Plan for 2 days.",
                    "The expiration date for this plan is ",
                    "Confirm purchase? (yes/no)",
                    "************* Parking plan purchased successfully *************"
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("parkingPlanrOneMonth")
    void parkingPlanrOneMonth(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> parkingPlanrOneMonth() {
        return Stream.of(
            Arguments.of(
            	"2\n" +
            	"2222\n" +
            	"1\n" +
            	"yes\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "How many months of monthly parking plan to purchase?",
                    "Purchasing Monthly Parking Plan for 1 months.",
                    "The expiration date for this plan is ",
                    "Confirm purchase? (yes/no)",
                    "************* Parking plan purchased successfully *************"
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("parkingPlanOneYear")
    void parkingPlanOneYear(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> parkingPlanOneYear() {
        return Stream.of(
            Arguments.of(
            	"3\n" +
            	"3333\n" +
            	"1\n" +
            	"yes\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "How many years of annual parking plan to purchase?",
                    "Purchasing Annual Parking Plan for 1 years.",
                    "The expiration date for this plan is ",
                    "Confirm purchase? (yes/no)",
                    "************* Parking plan purchased successfully *************"
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDriverIdDay")
    void invalidDriverIdDay(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> invalidDriverIdDay() {
        return Stream.of(
            Arguments.of(
            	"1\n" +
            	"invalid_ID\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "************* Invalid driver ID *************",
                    "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidDriverIdMonth")
    void invalidDriverIdMonth(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> invalidDriverIdMonth() {
        return Stream.of(
            Arguments.of(
            	"2\n" +
            	"invalid_ID\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "************* Invalid driver ID *************",
                    "========================================================================================================="
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidDriverIdYear")
    void invalidDriverIdYear(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> invalidDriverIdYear() {
        return Stream.of(
            Arguments.of(
            	"3\n" +
            	"invalid_ID\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "************* Invalid driver ID *************",
                    "========================================================================================================="
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("parkingPlanDayCancel")
    void parkingPlanDayCancel(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> parkingPlanDayCancel() {
        return Stream.of(
            Arguments.of(
            	"1\n" +
            	"1234\n" +
            	"2\n" +
            	"no\n",
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "How many days of daily parking plan to purchase?",
                    "Purchasing Daily Parking Plan for 2 days.",
                    "The expiration date for this plan is ",
                    "Confirm purchase? (yes/no)",
                    "************* Purchase cancelled *************"
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("parkingPlanMonthCancel")
    void parkingPlanMonthCancel(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> parkingPlanMonthCancel() {
        return Stream.of(
            Arguments.of(
            	"2\n" +
            	"2222\n" +
            	"1\n" +
            	"no\n",
                new String[]{
                	"The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "How many months of monthly parking plan to purchase?",
                    "Purchasing Monthly Parking Plan for 1 months.",
                    "The expiration date for this plan is ",
                    "Confirm purchase? (yes/no)",
                    "************* Purchase cancelled *************"
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("parkingPlanAnnualCancel")
    void parkingPlanAnnualCancel(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> parkingPlanAnnualCancel() {
        return Stream.of(
            Arguments.of(
            	"3\n" +
            	"3333\n" +
            	"1\n" +
            	"no\n",
                new String[]{
                	"The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "Enter parking plan to purchase:",
                    "Enter the driver's ID for the parking plan:",
                    "How many years of annual parking plan to purchase?",
                    "Purchasing Annual Parking Plan for 1 years.",
                    "The expiration date for this plan is ",
                    "Confirm purchase? (yes/no)",
                    "************* Purchase cancelled *************"
                }
            )
        );
    }
    

    @ParameterizedTest
    @MethodSource("exitMenu")
    void exitMenu(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> exitMenu() {
        return Stream.of(
            Arguments.of(
            	"4\n" ,
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "************ Exiting parking plan selection *************",
                    "========================================================================================================="
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidOption")
    void invalidOption(
            String simulatedInput,
            String[] expectedOutputs) {
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("PARKING_PLAN", staff);

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), "Missing: " + expected);
        }

    }
    
    static Stream<Arguments> invalidOption() {
        return Stream.of(
            Arguments.of(
            	"5\n" ,
                new String[]{
                    "The following parking plans are available:",
                    "1. Daily Parking Plan",
                    "2. Monthly Parking Plan",
                    "3. Annual Parking Plan",
                    "4. Exit",
                    "************* Invalid Option *************",
                    "========================================================================================================="
                }
            )
        );
    }
}