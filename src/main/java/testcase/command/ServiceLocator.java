package testcase.command;

public interface ServiceLocator {
    Command getCommand(String commandName);
}
