package server;

import java.util.ArrayList;

public class RoomManager {
	private static RoomManager _instance = new RoomManager();
	private static ArrayList<Room> rooms = new ArrayList<Room>();
	
	private RoomManager() {};
	
	public static RoomManager getInstance() {
		return _instance;
	}
	
	public Boolean addRoom(String name) {
		//Room already exists
		if(RoomManager.doesRoomExist(name)) return false;
			
		Room newRoom = new Room(name);
		rooms.add(newRoom);
		return true;
		
	}
	
	public void removeRoom(String name) {
		rooms.remove(getRoomByName(name));
	}
	
	public Room getRoomByName(String name) {
		for(Room r : rooms) {
			if(r.getName().equals(name)) return r;
		}
		
		return null;
	}
	
	public static boolean doesRoomExist(String roomName) {
		for(Room r : rooms) {
			if(roomName.equals(r.getName())) return true;
		}
		
		return false;
	}


}
