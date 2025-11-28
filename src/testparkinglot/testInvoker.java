package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parkinglot.*;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Scanner;

class testInvoker {
    private CmdRegisterDriver cmd;
    private final String TEST_COMMAND_NAME = "REGISTER_DRIVER";
    
    @BeforeEach
    void setup() throws Exception{
        cmd = new CmdRegisterDriver();

        Scanner testScanner = new Scanner("1\n10.5\nyes\n");
        Invoker invoker = Invoker.getInstance();
        Field commandListField = Invoker.class.getDeclaredField("commandList");
        commandListField.setAccessible(true);
        
        Map<String, Command> customCommandList = Map.of(
            "REGISTER_DRIVER", cmd
        );
        
        commandListField.set(invoker, customCommandList);
    }

    @Test
    void getInstance_shouldReturnSameInstance() {
        Invoker firstInstance = Invoker.getInstance();
        Invoker secondInstance = Invoker.getInstance();

        assertNotNull(firstInstance);
        assertSame(firstInstance, secondInstance);
    }
    

    @Test
    void validCommand() {
        Invoker instance  = Invoker.getInstance();
        Scanner testScanner = new Scanner("1\n1\n1\n2\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        instance.executeCommand(TEST_COMMAND_NAME);
    }

}