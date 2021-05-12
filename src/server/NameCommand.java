package server;

import java.util.ArrayList;

public class NameCommand extends Command{
	
	private String commandName = "\\name";
	private int minArgs = 1;
	private int maxArgs = 1;

	@Override
	public Boolean executeCommand(User user, ArrayList<String> args) {
		if(!validateArgs(args,user)) return false;
		
		
		String userName = args.get(0);
		user.setName(userName);
		user.receiveMessage("Name successfully set to " + userName);
		return true;
		
	}

	@Override
	public String getCommandName() {
		return this.commandName; 
	}
	
	private Boolean validateArgs(ArrayList<String> args, User user) {
		//Must have name as argument
		if(args == null) {
			user.receiveMessage("You must enter your desired nickname after the \\name command");
			return false;
		}
		
		if(args.size() > maxArgs) {
			user.receiveMessage("Failed to set name. The \\name command should have only one argument: your desired nickname.");
			return false;
		}
		
		return true;
	}

}
