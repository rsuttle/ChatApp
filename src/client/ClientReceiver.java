package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Listens for messages from the server. When message is received, prints the
 * message to standard output.
 * 
 */
public class ClientReceiver implements Runnable {
	Socket clientSocket;
	
	public ClientReceiver(Socket s) {
		this.clientSocket = s;
	}
	
	public void run() {
		try(
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		){
			//Listen for incoming messages
			String text = "";
			while((text = in.readLine()) != null) {
				System.out.println(text);
			}
		}
		catch (Exception e) {
			System.out.println("Error " +e);
		}
	}
}
