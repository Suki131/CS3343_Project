package parkinglot;
import java.util.Map;

public class DriverInvoker {
    private Map<String, DriverCommand> driverCommandList;
    private static DriverInvoker instance = new DriverInvoker();

    private DriverInvoker() {
        driverCommandList = Map.of(
                "REGISTER_VEHICLE", new CmdRegisterVehicle(),
                "PICK_UP_VEHICLE", new CmdPickUpVehicle(),
                "MAKE_PAYMENT", new CmdMakePayment()
        );
    }

    public static DriverInvoker getInstance() {
        return instance;
    }

    public void executeCommand(String cmdName, Driver driver) {
        DriverCommand command = driverCommandList.get(cmdName);
        if (command != null) {
            command.execute(cmdName, driver);
        } else {
            System.out.println("Invalid command: " + cmdName);
        }
    }

}
