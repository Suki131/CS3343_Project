package src.parkinglot;
import java.util.Map;

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
        if (command != null) {
            command.execute(cmdName);
        } else {
            System.out.println("Invalid command: " + cmdName);
        }
    }
}
