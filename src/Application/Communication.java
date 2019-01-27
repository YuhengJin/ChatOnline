package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*	public class Communication {
		
		 ServerSocket serverSocket = null;
		  Socket clientSocket = null;
		  InputStream is = null;
	
		
	public Communication(int numPort, int numPort2) {
		
		try
	    {
	      serverSocket = new ServerSocket(10080);
	      clientSocket= serverSocket.accept();
	 
	      is = clientSocket.getInputStream();
	      int c;
	      while((c = is.read()) != -1)
	        System.out.write(c);
	    }
		catch(Exception e)
	    {
	      System.err.println("Pb de co " + e.toString());
	    }
	}
	
	public void send (String msg) throws IOException {
		System.out.println("envoi msg A depuis com");

		//Put the server into a waiting state, listen for incoming connection
		Socket link = this.serverSocket.accept();
		PrintWriter out = new PrintWriter(link.getOutputStream(),true);
		//out.println(new Date().toString());
		out.println(msg);
		try {
			link = this.serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void receive () throws IOException {
		
		BufferedReader input =  new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); 
		String answer = input.readLine() ;
		System.out.println(answer);
		this.clientSocket.close();

	}
	

}
*/