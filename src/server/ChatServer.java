package server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
	
	private static final int MAX_THREADS = 8;
	
	private static HashMap<SocketChannel,User> socketChannelToUser;
	private static ExecutorService threadPool;
	private static ServerSocketChannel serverSocketChannel;
	private static Selector selector;
	private static RoomManager roomManager;
	private static CommandManager cmdManager;
	
	public static void main(String[] args) {
		
		//Set up default rooms
		roomManager = RoomManager.getInstance();
		roomManager.addRoom("main");
		
		//Register commands
		cmdManager = CommandManager.getInstance();
		Command joinCmd = new JoinCommand();
		Command nameCmd = new NameCommand();
		Command helpCmd = new HelpCommand();
		Command createRoomCmd = new CreateRoomCommand();
		cmdManager.registerCommand(joinCmd);
		cmdManager.registerCommand(nameCmd);
		cmdManager.registerCommand(helpCmd);
		cmdManager.registerCommand(createRoomCmd);
		
		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		
		//Keep track of socket-user pairs
		socketChannelToUser =  new HashMap<SocketChannel,User>();
		
		int port = Integer.parseInt(args[0]);
		
		try 
		{
			//Initialize selector and server's socket channel
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
			
			//Register channel to selector
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); 
		
			while(true) {
				//Blocks until at least 1 channel is ready (can be either a new client or message)
				selector.select();
				
				//Set of channels that are ready
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				for(Iterator<SelectionKey> it = selectedKeys.iterator();it.hasNext();) {
					
					SelectionKey connection = it.next();
					
					if(connection.isAcceptable()) {
						//New client connection, accept and register to selector
						acceptNewClientConnection(connection);
					} else if(connection.isReadable()) {
						handleIncomingClientMessage(connection);
					}
					
					it.remove();
				}
				
			}
		
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
		
	}
	
	public static void acceptNewClientConnection(SelectionKey connection) {
		try {
			SocketChannel newClient =  serverSocketChannel.accept();
			newClient.configureBlocking(false);
			SelectionKey newKey = newClient.register(selector, SelectionKey.OP_READ);
			newKey.attach(new ReadProgress());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void handleIncomingClientMessage(SelectionKey connection) throws IOException {
		SocketChannel client = (SocketChannel) connection.channel();
		ReadProgress readProgress = (ReadProgress) connection.attachment();
		int socketStatus = 0;
		
		try {
			socketStatus = client.read(readProgress.msgBuffer);
		} catch (IOException e) {
			//Handles case where client process is forcibly terminated
			User u = socketChannelToUser.get(client);
			u.getCurrentRoom().removeFromRoom(u);
			socketChannelToUser.remove(client);
			connection.cancel();
			
			
		}
		
		readProgress.processNewData();
		
		if(readProgress.isMessageComplete()) {
			
			User user;
			if(socketChannelToUser.containsKey(client)) {
				user = socketChannelToUser.get(client);
			} else {
				user = new User(client);
				socketChannelToUser.put(client, user);
			}
			
			Message msg = new Message(user,readProgress.msg);
			Runnable r = new MessageTask(msg);
			threadPool.execute(r);

			readProgress.reset();
			if(socketStatus == -1) {
				//Client disconnected
				User u = socketChannelToUser.get(client);
				u.getCurrentRoom().removeFromRoom(u);
				socketChannelToUser.remove(client);
			}
			
	
		}
	}

}
