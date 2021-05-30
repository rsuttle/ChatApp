package server;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Thread that handles a message from a User. Validates arguments, decides if the
 * message is a command, and then handles thet message accordingly.
 * 
 */
public class MessageTask implements Runnable {
	
	private Message msgObject;
	
	/**
	 * 
	 * @param msg Message object containing the User's message.
	 */
	public MessageTask(Message msg) {
		msgObject = msg;
	}

	/**
	 * Handles message.
	 */
	@Override
	public void run() {
		User sendingUser = msgObject.getSendingUser();
		String message = msgObject.getRawMessageText();
		String formattedMessage = msgObject.getFormattedMessage();
		
		if(message.startsWith("\\")) {
			handleCommand(message);
		} else {
			if(sendingUser.getName() == null) {
				sendingUser.receiveMessage("You need to create a nickname before sending messages. To do this, type: \\name [your name]");
				
			} else {
				sendingUser.sendMessage(formattedMessage);
			}
			
			
		}
	
	}
	
	/**
	 * Passes the command to the proper class for processing.
	 * @param text The message text.
	 */
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
			cmd.executeCommand(msgObject.getSendingUser(), argsList);
		} else {
			msgObject.getSendingUser().receiveMessage("Command not found.");
		}
		
	
		


		
	}

}
