package parkinglot;
import java.util.Map;
import java.util.Scanner;

public class CommandInvoker {
    private final Map<String, Command> CommandList;
    private final static CommandInvoker instance = new CommandInvoker();

    private CommandInvoker() {
        CommandList = Map.of(
                "REGISTER_DRIVER", new CmdRegisterDriver()
        );
    }

    public static CommandInvoker getInstance() {
        return instance;
    }

    public void executeCommand(String cmdName) {
        Command command = CommandList.get(cmdName);
        command.execute(cmdName);
    }
}
