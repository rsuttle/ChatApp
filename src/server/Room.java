package server;

import java.util.ArrayList;

public class Room {
	private String name;
	private ArrayList<User> usersInRoom = new ArrayList<User>();
	
	public Room(String name) {
		this.name = name;
	}
	
	public void broadcastMessage(String msg, User sendingUser) {
		for(User u : usersInRoom) {
			//if(u != sendingUser) u.receiveMessage(msg);
			u.receiveMessage(msg);
		}
	}

	public String getName() {
		return this.name;
	}
	
	public void addToRoom(User u) {
		usersInRoom.add(u);
	}
	
}
