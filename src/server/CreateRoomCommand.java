package server;

import java.util.ArrayList;

/**
 * 
 * Validates arguments and executes the \create command.
 *
 */
public class CreateRoomCommand implements Command {
	private String commandName = "\\create";
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
		
		String roomName = args.get(0);
		RoomManager rmgr = RoomManager.getInstance();
		if(rmgr.addRoom(roomName)) {
			user.receiveMessage("Room successfully created!");
		}else {
			user.receiveMessage("Room already exists. To join the room, use the \\join command.");
		}
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
		//Must have room name as argument
		if(args == null) {
			user.receiveMessage("You must enter the new room name after the \\room command");
			return false;
		}
		
		if(args.size() > maxArgs) {
			user.receiveMessage("Failed to create room. The \\room command should have only one argument: the name of the new room.");
			return false;
		}
		
		return true;
	}

}
