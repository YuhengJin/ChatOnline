package Application;

import java.net.InetAddress;
import java.net.Socket;

public class Main_Test {

	public static void main(String[] args) throws Exception {	
		/*
		 * La partie connection TCP entre server et client
		 */
		
		/*User userA = new User ("Pierre",InetAddress.getByName("127.0.0.1"), 2023); 
		User userB = new User ("Stephan",InetAddress.getByName("127.0.0.1"), 2024); 
		User userC = new User ("Yuheng",InetAddress.getByName("127.0.0.1"), 2025); 
	
		
		userA.connecter(2024);
		userA.sendMessage("Hello!  "+userB.get_Name()+"I'm User  "+userA.get_Name());
		
		
		userA.connecter(2025);
		userA.sendMessage("Hello!  "+userC.get_Name()+"I'm User "+userA.get_Name());
		
		
		
		userB.connecter(2023);
	
		userA.disconnect();*/
		
		
		
		
		/*
		 * La partie brodcast et multicast  UDP
		 */
		
	
		String name="Yuheng"; int port =2025;
		Chat u = new Chat(name, port);
		System.out.println(name + " is running" + " listeningport "+ port);
		u.initrceivemessage();
		u.multicast("newuser/" + name + "/" + Integer.toString(port));
	
		

		

		String name2="Nico"; int port2 =2026;
		Chat u2 = new Chat(name2, port2);
		System.out.println(name2 + " is running" + " listeningport "+ port2);
		u2.initrceivemessage();
		u2.multicast("newuser/" + name2 + "/" + Integer.toString(port2));
		
		
		
	}
}
