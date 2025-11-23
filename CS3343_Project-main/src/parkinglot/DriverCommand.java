package parkinglot;

import java.util.Scanner;

public interface DriverCommand {
    void execute(String cmdName, Driver driver);
}
