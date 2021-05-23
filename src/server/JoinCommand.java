package server;

import java.util.ArrayList;

public class JoinCommand implements Command{

	private String commandName = "\\join";
	private int minArgs = 1;
	private int maxArgs = 1;
	
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
	
	public String getCommandName() {
		return this.commandName;
	}
	
	
	
	

}
