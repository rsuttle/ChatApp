package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Represents a user currently on the server.
 *
 */
public class User {
	private SocketChannel userSocket;
	private Room currentRoom;
	private String name = null;
	
	/**
	 * Constructor for User class.
	 * 
	 * @param sock SocketChannel to be paired with user. Messages will be sent
	 * through this socket channel.
	 */
	public User(SocketChannel sock) {
		this.userSocket = sock;
		//Put user in default room (main) 
		RoomManager rmgr = RoomManager.getInstance();
		rmgr.getRoomByName("main").addToRoom(this);
		this.currentRoom = rmgr.getRoomByName("main");
	}
	
	/**
	 * Sends a message from this User to every other User in their current room.
	 * 
	 * @param msg The message string to be sent.
	 */
	public void sendMessage(String msg) {
		currentRoom.broadcastMessage(msg,this);
	}
	
	/**
	 * Sends a message from this User to every other User in their current room.
	 * 
	 * @param msg The Message object.
	 */
	public void sendMessage(Message msg) {
		currentRoom.broadcastMessage(msg.getFormattedMessage(),this);
	}
	
	/**
	 * Sends a message to this User.
	 * 
	 * @param msg The message string to be sent.
	 */
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
	
	/**
	 * Attempts to put this User into specified room. If the room does not
	 * exist, this User will remain in their current room and will be sent
	 * a message informing them that the room could not be joined.
	 * 
	 *  @param roomName The name of the room this User will attempt to join.
	 *  
	 *  @return True if the room was successfully joined, false otherwise.
	 * 
	 */
	public boolean joinRoom(String roomName) {
		RoomManager rmgr = RoomManager.getInstance();
		if(!rmgr.doesRoomExist(roomName)) return false;
		currentRoom.removeFromRoom(this);
		Room roomToJoin = rmgr.getRoomByName(roomName);
		roomToJoin.addToRoom(this);
		currentRoom = roomToJoin;
		return true;
		
	}

	/**
	 * 
	 * @return This user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name The string this User's name will be set to.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return This User's current room.
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
}
