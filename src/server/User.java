package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class User {
	private SocketChannel userSocket;
	private Room currentRoom;
	private String name = null;
	
	public User(SocketChannel s) {
		this.userSocket = s;
		//Put user in default room (main) 
		RoomManager rmgr = RoomManager.getInstance();
		rmgr.getRoomByName("main").addToRoom(this);
		this.currentRoom = rmgr.getRoomByName("main");
	}
	
	public void sendMessage(String msg) {
		currentRoom.broadcastMessage(msg,this);
	}
	
	public void sendMessage(Message msg) {
		currentRoom.broadcastMessage(msg.getFormattedMessage(),this);
	}
	
	public void receiveMessage(String msg) {
		try 
		{
			
			msg += "\r\n";
			ByteBuffer out = ByteBuffer.wrap(msg.getBytes());
			while(out.hasRemaining()) {
				userSocket.write(out);
		}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean joinRoom(String roomName) {
		RoomManager rmgr = RoomManager.getInstance();
		if(!rmgr.doesRoomExist(roomName)) return false;
		currentRoom.removeFromRoom(this);
		Room roomToJoin = rmgr.getRoomByName(roomName);
		roomToJoin.addToRoom(this);
		currentRoom = roomToJoin;
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
