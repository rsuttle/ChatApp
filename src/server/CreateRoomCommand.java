package server;

import java.util.ArrayList;

public class CreateRoomCommand extends Command {
	private String commandName = "\\create";
	private int minArgs = 1;
	private int maxArgs = 1;

	@Override
	public Boolean executeCommand(User user, ArrayList<String> args) {
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

	@Override
	public String getCommandName() {
		return this.commandName;
	}
	
	private Boolean validateArgs(ArrayList<String> args, User user) {
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
