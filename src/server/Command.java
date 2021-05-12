package server;

import java.util.ArrayList;

public abstract class Command {
	
	
	public abstract Boolean executeCommand(User user, ArrayList<String> args);

	public abstract String getCommandName();
	
	
	
}
