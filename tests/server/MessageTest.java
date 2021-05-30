package server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Tests basic functionality of the server.
 */
@TestInstance(Lifecycle.PER_CLASS)
class MessageTest {
	final String ip = "192.168.0.5";
	final int port = 9000;
	
	Socket client;
	SocketChannel clientSocketChannel;
	User testUser;
	BufferedReader in;
	
	@BeforeAll
	void setUp() throws IOException {
		//Set up a server and client for the tests
		setUpRoomsAndCommands();
		ServerSocketChannel testServer = ServerSocketChannel.open();
		testServer.socket().bind(new InetSocketAddress(port));
		client = new Socket(ip,port);
		clientSocketChannel = testServer.accept();
		testUser = new User(clientSocketChannel);
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
	}

	@Test
	void testTellUserToSetNameOnFirstMessage() throws IOException {
		Message testMsg = new Message(testUser, "Hello");
		MessageTask testTask = new MessageTask(testMsg);
		testTask.run();
		String receivedMessage = getClientReceivedMessage();
		
		assertEquals(receivedMessage, "You need to create a nickname before sending messages. To do this, type: \\name [your name]");
		
	}
	
	@Test
	void testUserCorrectlyAttemptsToSetName() throws IOException {
		Message testMsg = new Message(testUser, "\\name George");
		MessageTask testTask = new MessageTask(testMsg);
		testTask.run();
		String receivedMessage = getClientReceivedMessage();
		
		assertEquals(receivedMessage, "Name successfully set to George");
		assertNotNull(testUser.getName());
	}
	
	@Test 
	void testUserCreatesANewRoom() throws IOException{
		Message testMsg = new Message(testUser, "\\create sports");
		MessageTask testTask = new MessageTask(testMsg);
		testTask.run();
		String receivedMessage = getClientReceivedMessage();
		
		assertEquals(receivedMessage, "Room successfully created!");
		assertNotNull(testUser.getCurrentRoom());
	}
	
	@Test 
	void testUserJoinsMainRoom() throws IOException{
		Message testMsg = new Message(testUser, "\\join main");
		MessageTask testTask = new MessageTask(testMsg);
		testTask.run();
		String receivedMessage = getClientReceivedMessage();
		
		assertEquals(receivedMessage, "Successfully joined room: main");
		assertNotNull(testUser.getCurrentRoom());
		assertEquals(testUser.getCurrentRoom().getName(), "main");
	}
	
	@Test 
	void testUserJoinsNonExistentRoom() throws IOException{
		Message testMsg = new Message(testUser, "\\join room123");
		MessageTask testTask = new MessageTask(testMsg);
		testTask.run();
		String receivedMessage = getClientReceivedMessage();
		
		assertEquals(receivedMessage, "Room does not exist...please try again.");
		
	}
	
	@Test 
	void testUserSendsImproperlyFormattedCommand() throws IOException{
		Message testMsg = new Message(testUser, "\\name Michael Scott");
		MessageTask testTask = new MessageTask(testMsg);
		testTask.run();
		String receivedMessage = getClientReceivedMessage();
		
		assertEquals(receivedMessage, "Failed to set name. Your username must contain only one word.");
	}
	
	
	String getClientReceivedMessage() throws IOException {
		return in.readLine();
		
	}
	
	void setUpRoomsAndCommands() {
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
	}

}
