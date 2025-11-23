package parkinglot;
import java.util.Map;
import java.util.Scanner;

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
        command.execute(cmdName, staff);
    }

}
