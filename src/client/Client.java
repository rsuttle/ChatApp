package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		try (
			Socket s = new Socket(ip,port);
			PrintWriter out = new PrintWriter(s.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) 
		
		{
			Runnable listener = new ClientReceiver(s);
			Thread t = new Thread(listener);
			t.start();
			String text;
			while((text = stdIn.readLine()) != null) {
				out.println(text);
			}
		}
		catch (Exception e) {
			System.out.println("Error " +e);
		}
		
		
			
	}

}