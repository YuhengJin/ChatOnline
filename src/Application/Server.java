package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Application.Server;
import Application.Client.ListenServerMes;
import Interface.Graphique;

public class Server {

	private User user;
	private Chat chat;
	private ServerSocket socket;
	private int port;
	private Socket link;
	private Socket socket2;
	private PrintWriter out;
	private BufferedReader inputBuff;
	private CommunicateThread cThread;

	// private ArrayList<User> listUserConnecte = new ArrayList<User>();
	private String pseudo;

	public Server(int numPort, String name) {
		this.port = numPort;
		this.pseudo = name;
	}

	public Server(int numPort, User u, Chat c) {
		this.port = numPort;
		this.user = u; // The user qui veut connecter
		this.chat = c; // Lui meme
	}

	public void Startlistenning() {
		new Thread(new Runnable() {
			public void run() {
				try {

					// socket = new ServerSocket(port);
					socket = new ServerSocket(port);
					// System.out.println("port Server est "+port);

					while (true) {
						link = socket.accept();
						pseudo = chat.getUserName();
						System.out.println("[" + pseudo + ": Waiting for someone to connect ...]");
						cThread = new CommunicateThread(link);
						cThread.start();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void sendMesFromServer(String message) {
		// out = new PrintWriter(socket2.getOutputStream(), true);

		/*
		 * try { out = new PrintWriter(link.getOutputStream(), true); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		if (out == null) {
			throw new NullPointerException("Out is null.");
		}

		this.out.println(message);
		this.out.flush();
		if (message.equals("ServerFinishChat")) {
			closeAll();
		}
	}

	public void closeAll() {
		try {
			this.socket.close();
			this.link.close();
			this.out.close();
			this.inputBuff.close();
			cThread.stop();
			System.out.println("Reussir fermer server");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class CommunicateThread extends Thread {

		public CommunicateThread(Socket s) {
			System.out.println("[Nouvelle abonnement à " + pseudo + " de la part de " + s.getPort() + "]");
			socket2 = s;
			try {
				out = new PrintWriter(socket2.getOutputStream(), true);
				inputBuff = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
				if (inputBuff == null) {
					System.out.println("[can't get the inputBuff]");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			// Dialogue d = new Dialogue(chat, user, "Server");

			// int flag = 0;
			super.run();
			String result = null;
			try {
				while ((result = inputBuff.readLine()) != null) {
					if (result.equals("bye server")) {
						System.out.println("Capter le     ExpediteurFinishChat");
						// chat.getUsers().remove(user);
						inputBuff.close();
						out.close();
						link.close();
						break;
					} else {

						/*
						 * if (flag == 0) { // La 1er fois on connecte
						 * 
						 * System.out.println("La premiere fois  " + user.get_Name() + "  on connecte");
						 * 
						 * d = new Dialogue(chat, user, "Server"); d.showmessagerecu(user.get_Name() +
						 * " to " + chat.getUserName() + " : " + result); flag++; } else {
						 */
						// System.out.println( "------------------"+socket2.getPort());
						Graphique.showmessagerecu(user.get_Name() + " to " + chat.getUserName() + " : " + result);
						System.out.println(
								"Recu[server] Form Client[port:" + socket2.getPort() + "] Content is : " + result);
						// System.out.println( "-----"+socket2.getPort());
						// sendMesFromServer("Hi, bro how are you！"+socket2.getPort());
						// sendMesFromServer("ServerFinishChat");

//						System.out.println(
//								"To Client[port:" + socket2.getPort() + "] The response to client succes");

						// }

					}

					// inputBuff.close();
					// out.close();
					// socket2.close();

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String[] args)throws IOException { Server s = new
	 * Server(1502);//Create the server s.Startlistenning(); //Launch the thread
	 * 
	 * }
	 */

}
