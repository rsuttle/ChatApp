package server;

import java.util.ArrayList;

/**
 * 
 * Validates arguments and executes the \help command.
 *
 */
public class HelpCommand implements Command {
	private String commandName = "\\help";
	
	/**
	 * Executes the command.
	 * @param user User that is executing this command.
	 * @param args The arguments supplied with this command.
	 * @return True if command successfully executed, false otherwise (arguments
	 * were invalid)
	 */
	public boolean executeCommand(User user, ArrayList<String> args) {
		if(!validateArgs(args,user)) return false;
		
		user.receiveMessage("Use \\name to set or change your name. Use \\join to join a room. Use \\create to create a room. Use \\help to display help.");
		return true;
	}
	
	/**
	 * @return The name of this command.
	 */
	public String getCommandName() {
		return this.commandName;
	}
	
	/**
	 * Validates the arguments that were supplied with the command.
	 * @param args Arguments supplied by user.
	 * @param user User that sent the command and arguments.
	 * @return True if arguments are valid, false if not.
	 */
	private boolean validateArgs(ArrayList<String> args, User user) {
		//There should be no arguments
		if(args != null) {
			user.receiveMessage("The help command takes no arguments...please try again.");
			return false;
		}
		return true;
		
		
	}
}
