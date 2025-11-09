import java.util.Scanner;

public class CmdStaffLogin implements StaffCommand {

    @Override
    public void execute(String cmdName, Staff staff) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Staff ID to proceed.");
        String staffID = scanner.nextLine();
        System.out.println("Enter password to proceed.");
        String password = scanner.nextLine();
        System.out.println("Staff login successful. Welcome, " + staffID + "!");
    }

} 

