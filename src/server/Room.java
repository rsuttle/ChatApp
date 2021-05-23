package server;

import java.util.HashSet;
import java.util.Iterator;

public class Room {
	private String name;
	private HashSet<User> usersInRoom = new HashSet<User>();
	
	public Room(String name) {
		this.name = name;
	}
	
	public void broadcastMessage(String msg, User sendingUser) {
		Iterator<User> it = usersInRoom.iterator();
		while(it.hasNext()) {
			User u = (User) it.next();
			u.receiveMessage(msg);
		}
	
	}

	public String getName() {
		return this.name;
	}
	
	public void addToRoom(User u) {
		usersInRoom.add(u);
	}
	
	public void removeFromRoom(User u) {
		usersInRoom.remove(u);
	}
}
