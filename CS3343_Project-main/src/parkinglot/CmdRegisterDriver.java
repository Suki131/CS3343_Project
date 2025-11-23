package parkinglot;
import java.util.Scanner;

public class CmdRegisterDriver implements Command{
    Driver driver = null;
    CommandInvoker command = CommandInvoker.getInstance();
    DriverInvoker driverCommand = DriverInvoker.getInstance();
    StaffInvoker staffCommand = StaffInvoker.getInstance();    
    DriverManager driverManager = DriverManager.getInstance();

    @Override
    public void execute(String cmdName) {
        Scanner scanner = SmartParkingSystem.getScanner();
        while (true) {
            System.out.println("Do you have an account?\n 1. Yes\n 2. No\n 3. Back to Home Page");
            System.out.print("Enter (1-3) : ");
            String hasAccount = scanner.nextLine();
            if (hasAccount.equalsIgnoreCase("1")) {
                System.out.print("Enter your Driver ID : ");
                String driverID = scanner.nextLine();
                System.out.print("Enter your contact info : ");
                String contactInfo = scanner.nextLine();
                driver = driverManager.retrieveDriver(driverID, contactInfo);
                if (driver == null) {
                    System.out.println("=========================================================================================================");
                   System.out.println("*********** Driver not found. Please check your credentials ***********");
                   boolean continous2 = true;
                    while (continous2) {
                        System.out.print("Action :\n1. Re-enter Information\n2. Exit\nChoose your action : ");
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
                } else {
                    System.out.println("=========================================================================================================");
                    System.out.println("*********** Login successful! Welcome back, " + driver.getName() + " ***********");
                    driverCommand.executeCommand("REGISTER_VEHICLE", driver);
                    driver = null;
                    return;
                }
                
            } else if (hasAccount.equalsIgnoreCase("2")){
                System.out.println("Enter your name : ");
                String name = scanner.nextLine();
                System.out.println("Enter your contact info : ");
                String contactInfo = scanner.nextLine();
                driver = new Driver(name, contactInfo, MembershipType.NONE, null);
                DriverManager.getInstance().addDriver(driver);
                System.out.println("=========================================================================================================");
                System.out.println("*********** Registration successful! Your Driver ID is : " + driver.getDriverID() + " ***********");
                driverCommand.executeCommand("REGISTER_VEHICLE", driver);
                driver = null;
                return;
            } else if (hasAccount.equalsIgnoreCase("3")) {
                System.out.println("=========================================================================================================");
                return;
            }  else {
                System.out.println("Invalid option.");
                System.out.println("=========================================================================================================");
            }
        }
    }  
}
