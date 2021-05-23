package server;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Message{
	private User sendingUser;
	private String timeStamp;
	private String messageText;
	
	public Message(User sendingUser, String messageText) {
		this.sendingUser = sendingUser;
		this.messageText = messageText;
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM:dd:yyyy HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.timeStamp = dateFormatter.format(currentDate);
	}
	
	//Format is: @Username [hh:mm:ss]: Message content
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
	
	public String getRawMessageText() {
		return messageText;
	}

	public User getSendingUser() {
		return sendingUser;
	}
	
	
}
