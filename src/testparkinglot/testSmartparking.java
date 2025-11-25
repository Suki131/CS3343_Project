package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.SmartParkingSystem;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class testSmartparking extends inputOctopusAlipayCredit {

    private Thread runThread;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        
        // Reset scanner to ensure clean state
        SmartParkingSystem.resetScannerForTest();
    }

    @AfterEach
    void tearDown() {
        // Ensure thread is properly cleaned up
        if (runThread != null && runThread.isAlive()) {
            runThread.interrupt();
            try {
                runThread.join(1000); // Wait up to 1 second for thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("mainMenuScenarios")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testSmartParkingSystem_MainMenu(String input, String[] expected) {
        Scanner scanner = new Scanner(input);
        SmartParkingSystem.injectScannerForTest(scanner);

        // Run the system in a separate thread to handle infinite loop
        runThread = new Thread(() -> {
            try {
                SmartParkingSystem.getInstance().run();
            } catch (Exception e) {
                // Expected when thread is interrupted
            }
        });
        runThread.setDaemon(true);
        runThread.start();
        
        // Wait for the thread to process input
        try {
            Thread.sleep(1500); // Give time for processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Interrupt the thread to stop the infinite loop
        runThread.interrupt();
        
        // Wait a bit more to ensure output is captured
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String output = getOutput();
        for (String fragment : expected) {
            assertTrue(output.contains(fragment),
                () -> "Expected: '" + fragment + "' not found!\nActual output:\n" + output);
        }
    }

    static Stream<Arguments> mainMenuScenarios() {
        return Stream.of(
            // Scenario 1: Staff menu - Apply Discount (Case 1)
            Arguments.of(
                "1\n1\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "1. Apply Discount",
                    "Exiting Staff Menu"
                }
            ),
            
         // Scenario 199: Staff menu - Apply Discount (Case 1)
            Arguments.of(
                "1\n1\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "1. Apply Discount",
                    "  2. View Vehicle Record",
					"  3. Adjust Fee",
					"  4. Parking Plan",
					"  5. Check Vacancy",
					"  6. View Driver Info",
					"  7. Exit",
					"Please enter (1-7) to select next action : ",
                    "Exiting Staff Menu"
                }
            ),
            

            // Scenario 2: Staff menu - View Vehicle Record (Case 2)
            Arguments.of(
                "1\n2\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "2. View Vehicle Record",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 3: Staff menu - Adjust Fee (Case 3)
            Arguments.of(
                "1\n3\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "3. Adjust Fee",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 4: Staff menu - Parking Plan (Case 4)
            Arguments.of(
                "1\n4\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "4. Parking Plan",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 5: Staff menu - Check Vacancy (Case 5)
            Arguments.of(
                "1\n5\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "5. Check Vacancy",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 6: Staff menu - View Driver Info (Case 6)
            Arguments.of(
                "1\n6\n7\n",
                new String[] {
                    "Staff login successful",
                    "Action:",
                    "6. View Driver Info",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 7: Staff menu - Exit (Case 7)
            Arguments.of(
                "1\n7\n",
                new String[] {
                    "Staff login successful",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 8: Staff menu - Invalid option (Default case)
            Arguments.of(
                "1\n99\n7\n",
                new String[] {
                    "Staff login successful",
                    "Invalid action. Please enter (1-7)",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 9: Driver menu - Park vehicle (Option 1)
            Arguments.of(
                "2\n1\n3\n",
                new String[] {
                    "Are you want to",
                    "1. Park",
                    "2. Pick Up",
                    "Do you have an account?",
                    "3. Back to Home Page"
                }
            ),

            // Scenario 10: Driver menu - Pick Up vehicle (Option 2)
            Arguments.of(
                "2\n2\nD1001\nTEST123\n1\n91234567\n3\n",
                new String[] {
                    "Are you want to",
                    "1. Park",
                    "2. Pick Up",
                    "PICK_UP_VEHICLE"
                }
            ),

            // Scenario 11: Driver menu - Invalid option with recovery (Re-enter choice)
            Arguments.of(
                "2\n3\n1\n1\n3\n",
                new String[] {
                    "Invalid action. Please enter (1-2)",
                    "Action :",
                    "1. Re-enter choice",
                    "2. Exit",
                    "Do you have an account?"
                }
            ),

            // Scenario 12: Driver menu - Invalid option then exit
            Arguments.of(
                "2\n3\n2\n",
                new String[] {
                    "Invalid action. Please enter (1-2)",
                    "Action :",
                    "1. Re-enter choice",
                    "2. Exit"
                }
            ),

            // Scenario 13: Driver menu - Invalid recovery option
            Arguments.of(
                "2\n3\n3\n2\n",
                new String[] {
                    "Invalid action. Please enter (1-2)",
                    "Please enter 1 or 2"
                }
            ),

            // Scenario 14: Complete Driver Registration and Parking flow
            Arguments.of(
                "2\n1\n2\nTest Driver\n88889999\nTEST123\n1\n3\n",
                new String[] {
                    "Registration successful! Your Driver ID is",
                    "Vehicle registered successfully",
                    "parked at spot"
                }
            ),

            // Scenario 15: Driver Login and Parking flow
            Arguments.of(
                "2\n1\n1\nD1001\n88889999\nTEST124\n1\n3\n",
                new String[] {
                    "Login successful! Welcome back",
                    "Vehicle registered successfully",
                    "parked at spot"
                }
            ),

            // Scenario 16: Multiple invalid main menu inputs then valid
            Arguments.of(
                "3\n4\n5\n1\n7\n",
                new String[] {
                    "Invalid user type. Please enter 1 or 2",
                    "Staff login successful",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 17: Staff multiple commands then exit
            Arguments.of(
                "1\n1\n2\n3\n4\n5\n6\n7\n",
                new String[] {
                    "Staff login successful",
                    "Apply Discount",
                    "View Vehicle Record",
                    "Adjust Fee",
                    "Parking Plan",
                    "Check Vacancy",
                    "View Driver Info",
                    "Exiting Staff Menu"
                }
            ),

            // Scenario 18: Driver registration with different vehicle types
            Arguments.of(
                "2\n1\n2\nVan Driver\n77776666\nVAN123\n2\n3\n",
                new String[] {
                    "Registration successful! Your Driver ID is",
                    "Vehicle Type",
                    "1. Private Car",
                    "2. Van",
                    "Vehicle registered successfully"
                }
            ),

            // Scenario 19: Driver registration with truck
            Arguments.of(
                "2\n1\n2\nTruck Driver\n66665555\nTRUCK123\n3\n3\n",
                new String[] {
                	"Enter your name :",
                	"Enter your contact info : ",
                    "Registration successful! Your Driver ID is",
                    "Vehicle Type",
                    "3. Truck 3.5 Ton",
                    "Vehicle registered successfully"
                }
            ),

            // Scenario 20: Driver registration with invalid vehicle type then valid
            Arguments.of(
                "2\n1\n2\nTest Driver\n55554444\nTEST999\n5\n1\n3\n",
                new String[] {
                    "Invalid vehicle type",
                    "Vehicle registered successfully"
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("driverWorkflowScenarios")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testDriverWorkflows(String input, String[] expected) {
        Scanner scanner = new Scanner(input);
        SmartParkingSystem.injectScannerForTest(scanner);

        // Run the system in a separate thread to handle infinite loop
        runThread = new Thread(() -> {
            try {
                SmartParkingSystem.getInstance().run();
            } catch (Exception e) {
                // Expected when thread is interrupted
            }
        });
        runThread.setDaemon(true);
        runThread.start();
        
        // Wait for the thread to process input
        try {
            Thread.sleep(1500); // Give time for processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Interrupt the thread to stop the infinite loop
        runThread.interrupt();
        
        // Wait a bit more to ensure output is captured
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String output = getOutput();
        for (String fragment : expected) {
            assertTrue(output.contains(fragment),
                () -> "Expected: '" + fragment + "' not found!\nActual output:\n" + output);
        }
    }

    static Stream<Arguments> driverWorkflowScenarios() {
        return Stream.of(
            // Complete driver journey: Register → Park
            Arguments.of(
                "2\n1\n2\nComplete Driver\n11112222\nCOMPLETE1\n1\n3\n",
                new String[] {
                    "Registration successful!",
                    "Vehicle registered successfully",
                    "parked at spot"
                }
            ),

            // Driver with existing account journey: Login → Park
            Arguments.of(
                "2\n1\n1\nD1001\n11112222\nEXISTING1\n1\n3\n",
                new String[] {
                    "Login successful! Welcome back",
                    "Vehicle registered successfully",
                    "parked at spot"
                }
            ),

            // Driver pick up flow
            Arguments.of(
                "2\n2\nD1001\nTEST123\n1\n91234567\n3\n",
                new String[] {
                    "PICK_UP_VEHICLE",
                    "Payment successful",
                    "has been picked up"
                }
            ),

            // Driver registration then immediate exit
            Arguments.of(
                "2\n1\n3\n",
                new String[] {
                    "Do you have an account?",
                    "Back to Home Page"
                }
            ),

            // Driver login failure then successful registration
            Arguments.of(
                "2\n1\n1\nWRONGID\nWRONGPHONE\n2\nNew Driver\n33334444\nNEWCAR\n1\n3\n",
                new String[] {
                    "Driver not found. Please check your credentials",
                    "Registration successful!"
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("edgeCaseScenarios")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testEdgeCases(String input, String[] expected) {
        Scanner scanner = new Scanner(input);
        SmartParkingSystem.injectScannerForTest(scanner);

        // Run the system in a separate thread to handle infinite loop
        runThread = new Thread(() -> {
            try {
                SmartParkingSystem.getInstance().run();
            } catch (Exception e) {
                // Expected when thread is interrupted
            }
        });
        runThread.setDaemon(true);
        runThread.start();
        
        // Wait for the thread to process input
        try {
            Thread.sleep(1500); // Give time for processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Interrupt the thread to stop the infinite loop
        runThread.interrupt();
        
        // Wait a bit more to ensure output is captured
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String output = getOutput();
        for (String fragment : expected) {
            assertTrue(output.contains(fragment),
                () -> "Expected: '" + fragment + "' not found!\nActual output:\n" + output);
        }
    }

    static Stream<Arguments> edgeCaseScenarios() {
        return Stream.of(
            // Empty input handling
            Arguments.of(
                "\n1\n7\n",
                new String[] {
                    "Invalid user type",
                    "Staff login successful"
                }
            ),

            // Very long inputs
            Arguments.of(
                "2\n1\n2\nVery Long Name That Exceeds Normal Length\n12345678901234567890\nVERYLONGPLATE123456789\n1\n3\n",
                new String[] {
                    "Registration successful!",
                    "Vehicle registered successfully"
                }
            ),

            // Special characters in inputs
            Arguments.of(
                "2\n1\n2\nDrivér Nämé\n+852-1234-5678\nPL@TE-123\n1\n3\n",
                new String[] {
                    "Registration successful!",
                    "Vehicle registered successfully"
                }
            ),

            // Multiple spaces in inputs
            Arguments.of(
                "2\n1\n2\n  Driver  With  Spaces  \n  8888  9999  \n  PLATE  123  \n1\n3\n",
                new String[] {
                    "Registration successful!",
                    "Vehicle registered successfully"
                }
            )
        );
    }

    @ParameterizedTest
    @MethodSource("navigationScenarios")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testMenuNavigation(String input, String[] expected) {
        Scanner scanner = new Scanner(input);
        SmartParkingSystem.injectScannerForTest(scanner);

        // Run the system in a separate thread to handle infinite loop
        runThread = new Thread(() -> {
            try {
                SmartParkingSystem.getInstance().run();
            } catch (Exception e) {
                // Expected when thread is interrupted
            }
        });
        runThread.setDaemon(true);
        runThread.start();
        
        // Wait for the thread to process input
        try {
            Thread.sleep(1500); // Give time for processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Interrupt the thread to stop the infinite loop
        runThread.interrupt();
        
        // Wait a bit more to ensure output is captured
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = getOutput();
        for (String fragment : expected) {
            assertTrue(output.contains(fragment),
                () -> "Expected: '" + fragment + "' not found!\nActual output:\n" + output);
        }
    }

    static Stream<Arguments> navigationScenarios() {
        return Stream.of(
            // Staff → Driver → Staff navigation
            Arguments.of(
                "1\n7\n2\n1\n3\n1\n7\n",
                new String[] {
                    "Staff login successful",
                    "Exiting Staff Menu",
                    "Do you have an account?",
                    "Staff login successful",
                    "Exiting Staff Menu"
                }
            ),

            // Driver → Staff → Driver navigation
            Arguments.of(
                "2\n1\n3\n1\n7\n2\n1\n3\n",
                new String[] {
                    "Do you have an account?",
                    "Staff login successful",
                    "Exiting Staff Menu",
                    "Do you have an account?"
                }
            ),

            // Multiple staff sessions
            Arguments.of(
                "1\n1\n7\n1\n2\n7\n1\n3\n7\n",
                new String[] {
                    "Apply Discount",
                    "View Vehicle Record",
                    "Adjust Fee"
                }
            )
            
            
        );
    }
}