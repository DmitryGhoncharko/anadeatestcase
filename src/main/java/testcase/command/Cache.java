package testcase.command;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<String, Command> commandCache = new ConcurrentHashMap<>();

    public Optional<Command> getCommand(String commandName){
        return Optional.ofNullable(commandCache.get(commandName));
    }
    public void addCommand(String commandName, Command command){
        commandCache.put(commandName,command);
    }
}
