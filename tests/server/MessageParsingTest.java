package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the message parsing class.
 *
 */
class MessageParsingTest {
	ReadProgress readProgress;

	@BeforeEach
	void setUp() throws Exception {
		readProgress = new ReadProgress();
	}
	
	@Test
	void testMessageParsingWithWindowsEndOfLine() {
		String testMessage = "Hello\r\n";
		String expectedResult = "Hello";
		
		readProgress.msgBuffer.put(testMessage.getBytes());
		readProgress.processNewData();
		
		assertEquals(expectedResult,readProgress.msg);
	}
	
	@Test
	void testMessageParsingWithUnixEndOfLine() {
		String testMessage = "Hello\n";
		String expectedResult = "Hello";
		
		readProgress.msgBuffer.put(testMessage.getBytes());
		readProgress.processNewData();
		
		assertEquals(expectedResult,readProgress.msg);
	}
		
	
	@Test
	void testMessageParsingWithEmptyMessage() {
		String testMessage = "\r\n";
		String expectedResult = "";
		
		readProgress.msgBuffer.put(testMessage.getBytes());
		readProgress.processNewData();
		
		assertEquals(expectedResult,readProgress.msg);
	}
	
	@Test
	void testMessageParsingWithMultipleWords() {
		String testMessage = "Testing with multiple words!\r\n";
		String expectedResult = "Testing with multiple words!";
		
		readProgress.msgBuffer.put(testMessage.getBytes());
		readProgress.processNewData();
		
		assertEquals(expectedResult,readProgress.msg);
	}
	
	
}
