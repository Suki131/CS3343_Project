package parkinglot;
import java.util.Scanner;

public class SmartParkingSystem {
    private static final SmartParkingSystem smartParkingSystem = new SmartParkingSystem();
    private Scanner scanner;
    
    public static SmartParkingSystem getInstance() {
    	return smartParkingSystem;
    }
    
    public static Scanner getScanner() {
    	return getInstance().scanner;
    }
    
    public static void injectScannerForTest(Scanner testScanner) {
        getInstance().scanner = testScanner;
    }

    public static void resetScannerForTest() {
        getInstance().scanner = new Scanner(System.in);
    }
    
    public void displayStaffMenu() {
        CommandInvoker command = CommandInvoker.getInstance();
        DriverInvoker driverCommand = DriverInvoker.getInstance();
        StaffInvoker staffCommand = StaffInvoker.getInstance();
    	Scanner scanner = SmartParkingSystem.getScanner();

        System.out.println("Action: ");
        System.out.println("  1. Apply Discount");
        System.out.println("  2. View Vehicle Record");
        System.out.println("  3. Adjust Fee");
        System.out.println("  4. Parking Plan");
        System.out.println("  5. Check Vacancy");
        System.out.println("  6. View Driver Info");
        System.out.println("  7. Exit");
        System.out.print("Please enter (1-7) to select next action : ");
        String action = scanner.nextLine();
        System.out.println("=========================================================================================================");
        switch (action) {
        case "1":
            staffCommand.executeCommand("APPLY_DISCOUNT",null);
            break;
        case "2":
            staffCommand.executeCommand("VIEW_VEHICLE_RECORD",null);
            break;
        case "3":
            staffCommand.executeCommand("ADJUST_FEE",null);
            break;
        case "4":
            staffCommand.executeCommand("PARKING_PLAN",null);
            break;
        case "5":
            staffCommand.executeCommand("CHECK_VACANCY",null);
            break;
        case "7":
            System.out.println("Exiting Staff Menu.");
            break;
        case "6":
            staffCommand.executeCommand("VIEW_DRIVER_INFO",null);
            break;
        default:
            System.out.println("Invalid action. Please enter (1-7)");
        }
    }

    public void displayDriverMenu() {
        CommandInvoker command = CommandInvoker.getInstance();
        DriverInvoker driverCommand = DriverInvoker.getInstance();
        StaffInvoker staffCommand = StaffInvoker.getInstance();
        boolean continous1 = true;

        while (continous1) { 
            System.out.println("Are you want to\n  1. Park\n  2. Pick Up");
            System.out.print("Please enter (1-2) to select next action : ");
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("1")) {
                System.out.println("=========================================================================================================");
                command.executeCommand("REGISTER_DRIVER");
                continous1 = false;
            } else if (action.equalsIgnoreCase("2")) {
                System.out.println("=========================================================================================================");
                driverCommand.executeCommand("PICK_UP_VEHICLE", null);
                continous1 = false;
            } else {
                System.out.println("************ Invalid action. Please enter (1-2) ************");
                boolean continous2 = true;
                while (continous2) {
                    System.out.println("=========================================================================================================");
                    System.out.print("Action :\n1. Re-enter choice\n2. Exit\nChoose your action : ");
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            continous2 = false;
                            System.out.println("=========================================================================================================");
                            break;
                        case "2":
                            System.out.println("=========================================================================================================");
                            return;   
                        default:
                            System.out.println("=========================================================================================================");
                            System.out.println("****************** Please enter 1 or 2 ******************");
                    }
                }
            }
        }
    }

    public void printBig(String text) {
    String[][][] letters = {
            {{"WWW     WWW  ", "WWW     WWW  ", "WWW     WWW  ", "WW  WW  WWW  ", "WWWW   WWWW  ", "WWW     WWW  ", "WWW     WWW  "}},
            {{"EEEEEEEE ", "EE       ", "EE       ", "EEEEEEEE ", "EE       ", "EE       ", "EEEEEEEE "}},
            {{"LL       ", "LL       ", "LL       ", "LL       ", "LL       ", "LL       ", "LLLLLLLLL"}},
            {{" CCCCCCC ", "CC       ", "CC       ", "CC       ", "CC       ", "CC       ", " CCCCCCC "}},
            {{" OOOOOOO ", "OO     OO", "OO     OO", "OO     OO", "OO     OO", "OO     OO", " OOOOOOO "}},
            {{"MM     MM ", "MMM   MMM ", "MMMM MMMM ", "MM  M  MM ", "MM     MM ", "MM     MM ", "MM     MM "}}
        };

    String chars = "WELCOM";
    for (int row = 0; row < 7; row++) {
        StringBuilder line = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            int idx = chars.indexOf(c);
            line.append(letters[idx][0][row]).append(" ");
        }
        System.out.println(line.toString().trim());
    }
    }
    
    public void run(){
    	Scanner scanner = SmartParkingSystem.getScanner();
        System.out.println("=========================================================================================================");
        printBig("WELCOME");
        System.out.println("=========================================================================================================");
        System.out.println("Parking Lot Management System!");
        while (true) {

            System.out.print("Are you\n  1. Staff\n  2. Driver\nPlease Enter your Role (1-2): ");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("1")) {

                System.out.println("=========================================================================================================");
                smartParkingSystem.displayStaffMenu();
            } else if (userType.equalsIgnoreCase("2")) {

                System.out.println("=========================================================================================================");
                smartParkingSystem.displayDriverMenu();
            } else if (userType.equalsIgnoreCase("3")) {

                System.out.println("=========================================================================================================");
                return ;
            } else {
                System.out.println("Invalid user type. Please enter 1 or 2 to continous");
                System.out.println("=========================================================================================================");
            }
        }
    }

    public static void main(String[] args) {
        smartParkingSystem.run();
    }
}
