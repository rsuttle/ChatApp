package server;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Represents a chat room on the server. Main function is to keep track of where
 * users are at in the server, and to add and remove users from a room. It can also
 * be used to send a message to all of the users in the room. 
 */
public class Room {
	private String name;
	private HashSet<User> usersInRoom = new HashSet<User>();
	
	/**
	 * 
	 * @param name The name of the Room being created.
	 */
	public Room(String name) {
		this.name = name;
	}
	
	/**
	 * Sends a message to all of the Users in this Room.
	 * @param msg The message string that will be sent.
	 * @param sendingUser The User that is sending the message.
	 */
	public void broadcastMessage(String msg, User sendingUser) {
		Iterator<User> it = usersInRoom.iterator();
		while(it.hasNext()) {
			User u = (User) it.next();
			u.receiveMessage(msg);
		}
	
	}

	/**
	 * 
	 * @return The name of this Room.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @param u User to add to this Room.
	 */
	public void addToRoom(User u) {
		usersInRoom.add(u);
	}
	
	/**
	 * 
	 * @param u User to remove from this Room.
	 */
	public void removeFromRoom(User u) {
		usersInRoom.remove(u);
	}
}
