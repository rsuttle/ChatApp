package server;

import java.util.HashMap;

/**
 * This class manages the addition and deletion of the commands available
 * on the server. (Singleton)
 */
public class CommandManager {
	private static CommandManager _instance = new CommandManager();
	
	private HashMap<String,Command> availableCommands = new HashMap<String,Command>();
	
	/** This class should only have one instance. */
	private CommandManager() {};
	
	/**
	 * 
	 * @return The single instance of the CommandManager class.
	 */
	public static CommandManager getInstance() {
		return _instance;
	}
	
	/**
	 * 
	 * @param cmd The Command to register.
	 */
	public void registerCommand(Command cmd) {
		availableCommands.put(cmd.getCommandName(),cmd);
	}
	
	/**
	 * 
	 * @param cmd The Command to deregister.
	 */
	public void deregisterCommand(Command cmd) {
		availableCommands.remove(cmd.getCommandName());
	}
	
	/**
	 * 
	 * @param cmdString The name of the command to search for.
	 * @return True if the command exists, false otherwise.
	 */
	public boolean commandExists(String cmdString) {
		return availableCommands.containsKey(cmdString);
	}
	
	/**
	 * 
	 * @param cmdString The name of the command to get.
	 * @return The requested command.
	 */
	public Command getCommand(String cmdString) {
		return availableCommands.get(cmdString);
	}
}
