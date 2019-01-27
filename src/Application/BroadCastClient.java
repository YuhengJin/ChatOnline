package Application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadCastClient {

    private static DatagramSocket socket = null;
    private String pseudo ;
 
    public void main(String[] args) throws IOException {
        //broadcast("Hello", InetAddress.getByName("255.255.255.255"));
    }
 
    public void broadcast(String broadcastMessage, InetAddress address,String name) throws IOException {
    	pseudo=name ;
        socket = new DatagramSocket();
        socket.setBroadcast(true);
 
        byte[] buffer = broadcastMessage.getBytes();
 
        DatagramPacket packet  = new DatagramPacket(buffer, buffer.length, address, 4445);
        socket.send(packet);
		System.out.println("[BroadCast de connexion de la part de :"+pseudo+"]");

        socket.close();
    }
}