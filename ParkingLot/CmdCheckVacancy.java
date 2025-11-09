public class CmdCheckVacancy implements StaffCommand {
    
    @Override
    public void execute(String cmdName, Staff staff) {
        ParkingManager.displayParkingStatus();
    }
}
