package testcase.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandRegistry {
    private static final List<CommandRegistry> COMMAND_REGISTRY_LIST = Collections.emptyList();

    private final Command command;
    private final String commandName;
    private CommandRegistry(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }
    public Command getCommand() {
        return command;
    }

    static Command of(String name) {
        for (CommandRegistry constant : COMMAND_REGISTRY_LIST) {
            if (constant.commandName.equalsIgnoreCase(name)) {
                return constant.command;
            }
        }
        return null;
    }
}
