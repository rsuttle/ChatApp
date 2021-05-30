package server;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Holds information about a message, including the message content, the User that 
 * sent the message, and the time when the message was sent. 
 *
 */
public class Message{
	private User sendingUser;
	private String timeStamp;
	private String messageText;
	
	/**
	 * Gets the current time and constructs the Message object.
	 * @param sendingUser User that sent this message.
	 * @param messageText The text of the message.
	 */
	public Message(User sendingUser, String messageText) {
		this.sendingUser = sendingUser;
		this.messageText = messageText;
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM:dd:yyyy HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.timeStamp = dateFormatter.format(currentDate);
	}
	
	/**
	 * 
	 * @return The formatted message. The format is: "@Username [hh:mm:ss]: Message content"
	 */
	public String getFormattedMessage() {
		//Parse date
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("MM:dd:yyyy HH:mm:ss");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		Date msgDateTime;
		try {
			msgDateTime = dateTimeFormatter.parse(this.timeStamp);
			String formattedTime = timeFormatter.format(msgDateTime);
			return "@"+getSendingUser().getName()+" ["+formattedTime+"]: "+ messageText;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "@"+getSendingUser().getName()+"[**:**:**]: " + messageText;
		
	}
	
	/**
	 * 
	 * @return The text of the message, with no formatting.
	 */
	public String getRawMessageText() {
		return messageText;
	}

	/**
	 * 
	 * @return The User that sent this Message.
	 */
	public User getSendingUser() {
		return sendingUser;
	}
	
	
}
