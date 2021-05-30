package server;

import java.util.ArrayList;

/**
 * 
 * Validates arguments and executes the \name command.
 *
 */
public class NameCommand implements Command{
	
	private String commandName = "\\name";
	private int minArgs = 1;
	private int maxArgs = 1;

	/**
	 * Executes the command.
	 * @param user User that is executing this command.
	 * @param args The arguments supplied with this command.
	 * @return True if command successfully executed, false otherwise (arguments
	 * were invalid)
	 */
	public boolean executeCommand(User user, ArrayList<String> args) {
		if(!validateArgs(args,user)) return false;
		
		
		String userName = args.get(0);
		user.setName(userName);
		user.receiveMessage("Name successfully set to " + userName);
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
		//Must have name as argument
		if(args == null) {
			user.receiveMessage("You must enter your desired nickname after the \\name command");
			return false;
		}
		
		if(args.size() > maxArgs) {
			user.receiveMessage("Failed to set name. Your username must contain only one word.");
			return false;
		}
		
		return true;
	}

}
