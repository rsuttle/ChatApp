package server;

import java.util.HashMap;

public class CommandManager {
	private static CommandManager _instance = new CommandManager();
	
	private HashMap<String,Command> availableCommands = new HashMap<String,Command>();
	
	private CommandManager() {};
	
	public static CommandManager getInstance() {
		return _instance;
	}
	
	public void registerCommand(Command cmd) {
		availableCommands.put(cmd.getCommandName(),cmd);
	}
	
	public void deregisterCommand(Command cmd) {
		availableCommands.remove(cmd.getCommandName());
	}
	
	public Boolean commandExists(String cmdString) {
		return availableCommands.containsKey(cmdString);
	}
	
	public Command getCommand(String cmdString) {
		return availableCommands.get(cmdString);
	}
}
