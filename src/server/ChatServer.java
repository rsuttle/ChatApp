package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	
	

	public static void main(String[] args) {
		//ToDo: Make it so that room doesn't send user's message to the user that sent it
		//First arg can currently be a space, prob don't want that
		
		//Set up default rooms
		RoomManager roomManager = RoomManager.getInstance();
		roomManager.addRoom("main");
		
		//Register commands
		CommandManager cmdmgr = CommandManager.getInstance();
		Command joinCmd = new JoinCommand();
		Command nameCmd = new NameCommand();
		Command helpCmd = new HelpCommand();
		Command createRoomCmd = new CreateRoomCommand();
		cmdmgr.registerCommand(joinCmd);
		cmdmgr.registerCommand(nameCmd);
		cmdmgr.registerCommand(helpCmd);
		cmdmgr.registerCommand(createRoomCmd);
		
		
		int port = Integer.parseInt(args[0]);
		
		try (
				ServerSocket serverSocket = new ServerSocket(port);
				
			)
		
		{
			
			while(true) {
				Socket clientSocket = serverSocket.accept();
				User newUser = new User(clientSocket);
				Runnable r = new UserThread(clientSocket, newUser);
				Thread t = new Thread(r);
				t.start();
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
