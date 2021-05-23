package server;

import java.util.ArrayList;

public interface Command {
	
	
	public boolean executeCommand(User user, ArrayList<String> args);

	public String getCommandName();
	
	
	
}
