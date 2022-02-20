package testcase.command;

import java.util.Optional;

public class ServiceLocator {
    private static Cache cache = new Cache();

    public Command getCommand(String commandName){
        final Optional<Command> commandFromCache = cache.getCommand(commandName);
        if(commandFromCache.isPresent()){
            return commandFromCache.get();
        }
        InitialContext initialContext = new InitialContext();
        final Command command = initialContext.lookup(commandName);
        cache.addCommand(commandName,command);
        return command;
    }
}
