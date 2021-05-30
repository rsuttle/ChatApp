package server;

import java.util.ArrayList;

/**
 * Defines the Command interface. Every Command must provide a method to
 * execute the command, and a method to get the name of the command.
 *
 */
public interface Command {
	
	
	public boolean executeCommand(User user, ArrayList<String> args);

	public String getCommandName();
	
	
	
}
