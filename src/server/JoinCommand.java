package server;

import java.util.ArrayList;

/**
 * 
 * Validates arguments and executes the \join command.
 *
 */
public class JoinCommand implements Command{

	private String commandName = "\\join";
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
		if(!validateArgs(args,user)) {
			return false;
		}
		
		String roomName = args.get(0);
		if(user.joinRoom(roomName)) {
			user.receiveMessage("Successfully joined room: " + roomName);
			return true;
		}else{
			user.receiveMessage("Room does not exist...please try again.");
			return false;
		}
		
	}
	
	/**
	 * Validates the arguments that were supplied with the command.
	 * @param args Arguments supplied by user.
	 * @param user User that sent the command and arguments.
	 * @return True if arguments are valid, false if not.
	 */
	private boolean validateArgs(ArrayList<String> args, User user) {
		//Must have exactly one argument
		if(args == null) {
			user.receiveMessage("You must enter the name of the room after the \\join command");
			return false;
		}
		
		if(args.size() > maxArgs) {
			user.receiveMessage("Failed to join room. The \\join command should have only one argument: the name of the room.");
			return false;
		}
		
		return true;
	}
	
	/**
	 * @return The name of this command.
	 */
	public String getCommandName() {
		return this.commandName;
	}
	
	
	
	

}
