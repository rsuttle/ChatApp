package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class UserThread implements Runnable{
	
	private Socket clientSocket;
	private User user;
	
	public UserThread(Socket newClient, User newUser) {
		this.clientSocket = newClient;
		this.user = newUser;
	}
	
	private void handleCommand(String text) {
		CommandManager cmdmgr = CommandManager.getInstance();
		
		String[] splitText = text.split(" ",2);
		String commandString = splitText[0];
		
		ArrayList<String> argsList = null;
		//If it's not a one-word command
		if(splitText.length >= 2) {
			String args = splitText[1];
			argsList = new ArrayList<String>(Arrays.asList(args.split(" ")));
		}
		
		
		if(cmdmgr.commandExists(commandString)) {
			Command cmd = cmdmgr.getCommand(commandString);
			cmd.executeCommand(user, argsList);
		} else {
			user.receiveMessage("Command not found.");
		}
	}

	//Parse user input and handle accordingly
	public void run() {
		try (
				
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
			) 
			
			{
				String text;
				while((text = in.readLine()) != null) {
					
					if(text.startsWith("\\")) {
						handleCommand(text);
					} else {
						if(user.getName() == null) {
							user.receiveMessage("You need to create a nickname before sending messages. To do this, type: \\name [your name]");
							continue;
						}
						
						Message msg = new Message(user,text);
						user.sendMessage(msg);
					}
				
					
					
					
				}
			}
			catch (Exception e) {
				System.out.println("Error " +e);
			}
	}

}
