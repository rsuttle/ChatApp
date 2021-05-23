package server;

import java.nio.ByteBuffer;

public class ReadProgress {
	public ByteBuffer msgBuffer = ByteBuffer.allocate(512);
	public boolean hasReachedEnd = false;
	public String msg = "";
	public boolean prevCharWasCR = false;
	
	//Reads new message data from buffer, handling both Windows and Unix-style line endings
	public void processNewData() {
		msgBuffer.flip();
		//Need at least 1 byte to make a character
		while (msgBuffer.remaining() >= 1 && !hasReachedEnd){
			
			char c = (char) msgBuffer.get();
			
			if(c == '\r') {
				hasReachedEnd = true;
				prevCharWasCR = true;
			} else if(c == '\n') {
				//Handles Windows newline (CRLF)
				if(prevCharWasCR) {
					prevCharWasCR = false;
					
				} else {
					hasReachedEnd =  true;
				}
				
			} else {
				msg += c;
			}
			
		}
		msgBuffer.compact();
	}
	
	public boolean isMessageComplete() {
		return hasReachedEnd;
	}
	
	public void reset() {
		hasReachedEnd = false;
		msg = "";
	}
}
