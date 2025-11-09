import java.util.Map;

public class Invoker {
    private final Map<String, Command> commandList;
    private final static Invoker instance = new Invoker();

    private Invoker() {
        commandList = Map.of(
                "REGISTER_DRIVER", new CmdRegisterDriver()
        );
    }

    public static Invoker getInstance() {
        return instance;
    }

    public void executeCommand(String cmdName) {
        Command command = commandList.get(cmdName);
        if (command != null) {
            command.execute(cmdName);
        }
    }
}
