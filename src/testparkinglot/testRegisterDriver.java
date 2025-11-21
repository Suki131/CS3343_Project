package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.*;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testRegisterDriver extends inputOctopusAlipayCredit {

    private CmdRegisterDriver cmd;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        cmd = new CmdRegisterDriver();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
    }

    @ParameterizedTest
    @MethodSource("loginDriverScenarios")
    void shouldLoginSuccessfully(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> loginDriverScenarios() {
        return Stream.of(
            Arguments.of(
                "1\n" +
                "1234\n" +
                "12345678\n" +
                "AB123\n" +
                "1\n",

                new String[] {
                    "Do you have an account?",
                    "Enter your Driver ID",
                    "Enter your contact info",
                    "Login successful! Welcome back, John Doe"
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("registerDriver")
    void registerNewDriver(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> registerDriver() {
        return Stream.of(
            Arguments.of(
                "2\n" +
                "Jacky\n" +
                "88888888\n" +
                "AB123\n" +
                "1\n",

                new String[] {
                    "Do you have an account?",
                    "Enter your name : ",
                    "Enter your contact info : ",
                    "=========================================================================================================",
                    "*********** Registration successful! Your Driver ID is : "
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("exitCurrentMenu")
    void exitCurrentMenu(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> exitCurrentMenu() {
        return Stream.of(
            Arguments.of(
                "3\n",

                new String[] {
                    "Do you have an account?",
                    "=========================================================================================================",
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("invalidChoice")
    void invalidChoice(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> invalidChoice() {
        return Stream.of(
            Arguments.of(
                "4\n" + 
                "3\n",
                new String[] {
                    "Do you have an account?",
                    "Invalid option.",
                    "=========================================================================================================",
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("HasAcReenterSuccessfully")
    void HasAcReenterSuccessfully(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> HasAcReenterSuccessfully() {
        return Stream.of(
            Arguments.of(
                "1\n" + 
                "1111\n" + 
                "11111111\n" +
                "1\n" + 
                "1\n" + 
                "1234\n" +
                "12345678\n" +
                "AB123\n" +
                "1\n",
                new String[] {
                    "Do you have an account?\n 1. Yes\n 2. No\n 3. Back to Home Page",
                    "Enter (1-3) : ",
                    "Enter your Driver ID : ",
                    "Enter your contact info : ",
                    "=========================================================================================================",
                    "*********** Driver not found. Please check your credentials ***********",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "=========================================================================================================",
                    "Do you have an account?\n 1. Yes\n 2. No\n 3. Back to Home Page",
                    "Enter (1-3) : ",
                    "Enter your Driver ID : ",
                    "Enter your contact info : ",
                    
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("HasAcExit")
    void HasAcExit(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> HasAcExit() {
        return Stream.of(
            Arguments.of(
                "1\n" + 
                "1111\n" + 
                "11111111\n" +
                "2\n",
                new String[] {
                    "Do you have an account?\n 1. Yes\n 2. No\n 3. Back to Home Page",
                    "Enter (1-3) : ",
                    "Enter your Driver ID : ",
                    "Enter your contact info : ",
                    "=========================================================================================================",
                    "*********** Driver not found. Please check your credentials ***********",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "========================================================================================================="
                    
                }
            )
        );
    }
    
    @ParameterizedTest
    @MethodSource("HasAcInvalidInput")
    void HasAcInvalidInput(String simulatedInput, String[] expectedOutputs) {
        
        Scanner testScanner = new Scanner(simulatedInput);
        SmartParkingSystem.injectScannerForTest(testScanner);

        cmd.execute("REGISTER_DRIVER");

        String output = getOutput();

        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Missing expected output: " + expected);
        }
    }

    static Stream<Arguments> HasAcInvalidInput() {
        return Stream.of(
            Arguments.of(
                "1\n" + 
                "1111\n" + 
                "11111111\n" +
                "3\n" +
                "2\n",
                new String[] {
                    "Do you have an account?\n 1. Yes\n 2. No\n 3. Back to Home Page",
                    "Enter (1-3) : ",
                    "Enter your Driver ID : ",
                    "Enter your contact info : ",
                    "=========================================================================================================",
                    "*********** Driver not found. Please check your credentials ***********",
                    "Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ",
                    "=========================================================================================================",
                    "****************** Please enter 1 or 2 ******************"
                    
                }
            )
        );
    }
}