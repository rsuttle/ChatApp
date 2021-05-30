package server;

import java.util.ArrayList;

/**
 * This class manages the creation, deletion, and searching of all Room objects
 * in the server. (Singleton)
 *
 */
public class RoomManager {
	private static RoomManager _instance = new RoomManager();
	private static ArrayList<Room> rooms = new ArrayList<Room>();
	
	/** This class should only have one instance. */
	private RoomManager() {};
	
	/**
	 * 
	 * @return The single instance of the RoomManager class.
	 */
	public static RoomManager getInstance() {
		return _instance;
	}
	
	/**
	 * Adds a room.
	 * @param name Name of the new room.
	 * @return True if the room was successfully added, false if the room
	 * could not be added (likely because the room already exists).
	 */
	public boolean addRoom(String name) {
		//Room already exists
		if(RoomManager.doesRoomExist(name)) return false;
			
		Room newRoom = new Room(name);
		rooms.add(newRoom);
		return true;
		
	}
	
	/**
	 * Removes a room.
	 * @param name Name of the room to be removed.
	 */
	public void removeRoom(String name) {
		rooms.remove(getRoomByName(name));
	}
	
	/**
	 * Returns a Room object that matches the inputted string, or null
	 * if no Room is found.
	 * @param name Name of the room to search for.
	 * @return The Room object if it is found, null otherwise.
	 */
	public Room getRoomByName(String name) {
		for(Room r : rooms) {
			if(r.getName().equals(name)) return r;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param roomName The name of the room to check.
	 * @return True if the room exists, false if it does not exist.
	 */
	public static boolean doesRoomExist(String roomName) {
		for(Room r : rooms) {
			if(roomName.equals(r.getName())) return true;
		}
		
		return false;
	}


}
