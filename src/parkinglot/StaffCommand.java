package parkinglot;

import java.util.Scanner;

public interface StaffCommand {
    void execute(String cmdName, Staff staff);
}
