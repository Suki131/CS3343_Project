package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parkinglot.SmartParkingSystem;
import parkinglot.CommandInvoker;
import parkinglot.DriverInvoker;
import parkinglot.StaffInvoker;
import parkinglot.DriverManager;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
public class testSmartParkingSystemScannerNull {

    private CommandInvoker commandInvoker;
    private DriverInvoker driverInvoker;
    private StaffInvoker staffInvoker;
    private DriverManager driverManager;

    @BeforeEach
    void setUp() {
        
        commandInvoker = CommandInvoker.getInstance();
        driverInvoker = DriverInvoker.getInstance();
        staffInvoker = StaffInvoker.getInstance();
        driverManager = DriverManager.getInstance();
    }

    @Test
    public void shouldEnterNullBranchWhenScannerIsNull() {
        SmartParkingSystem.injectScannerForTest(null);
        
        Scanner scanner = SmartParkingSystem.getScanner(); 
        
        assertNotNull(scanner);
    }
}