package src.parkinglot;
import java.util.Map;

public class StaffInvoker {
    private final Map<String, StaffCommand> staffCommandList;
    private final static StaffInvoker instance = new StaffInvoker();

    private StaffInvoker() {
        staffCommandList = Map.of(
                "APPLY_DISCOUNT", new CmdApplyDiscount(),
                "VIEW_VEHICLE_RECORD", new CmdViewVehicleRecord(),
                "ADJUST_FEE", new CmdAdjustFee(),
                "PARKING_PLAN", new CmdParkingPlan(),
                "CHECK_VACANCY", new CmdCheckVacancy(),
                "VIEW_DRIVER_INFO", new CmdViewDriverInfo()
            );
    }

    public static StaffInvoker getInstance() {
        return instance;
    }

    public void executeCommand(String cmdName, Staff staff) {
        StaffCommand command = staffCommandList.get(cmdName);
        if (command != null) {
            command.execute(cmdName, staff);
        } else {
            System.out.println("Invalid command: " + cmdName);
        }
    }

}
