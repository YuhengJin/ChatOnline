package Session;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Session {
	private InetAddress desti;
	private int port;
	

	
	public Session(InetAddress i, int rt1) {
		desti = i;
		port = rt1;
	}
	
	
	public void sendmesssage(String message) {
		try {
			DatagramSocket ds = new DatagramSocket();
			byte [] data = message.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length,desti,port);
			ds.send(dp);
			ds.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
