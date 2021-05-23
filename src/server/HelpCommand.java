package server;

import java.util.ArrayList;

public class HelpCommand implements Command {
	private String commandName = "\\help";
	
	public boolean executeCommand(User user, ArrayList<String> args) {
		if(!validateArgs(args,user)) return false;
		
		user.receiveMessage("Use \\name to set or change your name. Use \\join to join a room. Use \\create to create a room. Use \\help to display help.");
		return true;
	}
	
	public String getCommandName() {
		return this.commandName;
	}
	
	private boolean validateArgs(ArrayList<String> args, User user) {
		//There should be no arguments
		if(args != null) {
			user.receiveMessage("The help command takes no arguments...please try again.");
			return false;
		}
		return true;
		
		
	}
}
