package testcase.command;

import java.util.Optional;

public class ServiceLocator {
    private static final Cache CACHE = new Cache();

    public Command getCommand(String commandName){
        final Optional<Command> commandFromCache = CACHE.getCommand(commandName);
        if(commandFromCache.isPresent()){
            return commandFromCache.get();
        }
        final InitialContext initialContext = new InitialContext();
        final Command command = initialContext.lookup(commandName);
        CACHE.addCommand(commandName,command);
        return command;
    }
}
