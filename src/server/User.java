package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
	private Socket userSocket;
	private Room currentRoom;
	private String name = null;
	
	public User(Socket s) {
		this.userSocket = s;
		//put user in default room for now
		RoomManager rmgr = RoomManager.getInstance();
		rmgr.getRoomByName("main").addToRoom(this);
		this.currentRoom = rmgr.getRoomByName("main");
	}
	
	public void sendMessage(String msg) {
		currentRoom.broadcastMessage(msg,this);
	}
	
	public void sendMessage(Message msg) {
		currentRoom.broadcastMessage(msg.getMessageAsString(),this);
	}
	
	public void receiveMessage(String msg) {
		try {
			PrintWriter out = new PrintWriter(userSocket.getOutputStream(),true);
			out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean joinRoom(String roomName) {
		RoomManager rmgr = RoomManager.getInstance();
		if(!rmgr.ifExists(roomName)) return false;
		this.currentRoom = rmgr.getRoomByName(roomName);
		return true;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
}
