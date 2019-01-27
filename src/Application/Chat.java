package Application;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Application.Server.CommunicateThread;
import Interface.Graphique;
import Session.Session;

public class Chat {
	//private User user;
	private String username;
	private List_users users;
	private int listeningport;
	DatagramSocket ds;
	MulticastSocket sock;
	Server server;
	Client client;
	private static Thread mutiThread;
	private static boolean running = true;
	private static Thread inimsaageThread;
	
	
	public Chat(String  username, int port) {
		//Context = this;
		this.username = username;
		this.listeningport = port;
		users = new List_users();

		try {
			ds = new DatagramSocket(listeningport);
		} catch (BindException bb) {
			System.out.println("Port deja utilise");
			//Display.showmessage("port is already used!");

		} catch (SocketException e) {
			e.printStackTrace();
		}

	}
	
	
	public void multicast(final String msg) {
		try {
			sock = new MulticastSocket(2222);
			InetAddress bcAddr = InetAddress.getByName("230.0.0.1");

			sock.joinGroup(bcAddr);
			sock.setLoopbackMode(false); 
			mutiThread = new Thread(new Runnable() {

				@Override
				public void run() {
					byte[] buffer = new byte[65507];
					DatagramPacket inpack = new DatagramPacket(buffer,
							buffer.length);

					while (running) {
						try {
						
							sock.receive(inpack);

							String info = new String(inpack.getData(), 0,
									inpack.getLength());

							System.out.println(username+"端  Print message recu： "+info);
							if (!info.split("/")[0].equals("quit*")) {
								// Means the user is on line
								String name = info.split("/")[0];
								int port = Integer.parseInt(info.split("/")[1]);

								User newuser = new User(name, inpack
										.getAddress(), port);

								Session session = new Session(newuser.get_Address(),
										newuser.get_Port());

								//There is  a new user
								if (!name.equals(username)&& !(port == listeningport)) {
									System.out.println("new user"+ new String(inpack.getData(), 0,inpack.getLength()));

									users.getUsers().add(newuser);
									Graphique.freshlist();
									users.printUserConnecte();
									//Graphique.freshlist();

									session.sendmesssage("newuser/" + username+ "/"+ Integer.toString(listeningport));

								} else if (name.equals(username)
										&& !(port == listeningport)) {
									session.sendmesssage("samename/");
								} else if (!name.equals(username)
										&& port == listeningport) {
									session.sendmesssage("sameport/");
								}
							}
							// user quit offline
							else if (!info.split("/")[1].equals(username)) {
								// respresent the user is off line
								String offlineusername = info.split("/")[1];
								String offlineport = info.split("/")[2];
								// delete the user name from the userslist
								// find the index of the offlineuser
								int i = 0;
								for (User u : users.getUsers()) {
									// Find the position of offlineuser
									if (u.get_Name().equals(offlineusername)&& u.get_Port() == (Integer.parseInt(offlineport))) {
										break;
									} else {
										i++;
									}

								}
								// delete user in the list
								users.getUsers().remove(i);
								
								Graphique.freshlist();
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
			mutiThread.start();
			// broadcast i'm on line
			broadcastmessage(username + "/" + listeningport);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void broadcastmessage(String msg) {
		try {
			InetAddress bcAddr = InetAddress.getByName("230.0.0.1");
			DatagramPacket outpack = new DatagramPacket(msg.getBytes(),msg.getBytes().length, bcAddr, 2222); 

			sock.send(outpack);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	public void initrceivemessage() {

		final Chat me = this;

		inimsaageThread = new Thread(new Runnable() {

			public void run() {
				try {
					while (running) {
						
						DatagramPacket dp = new DatagramPacket(new byte[256],
								256);
						ds.receive(dp);
						byte[] data = dp.getData();
						String info = new String(data).trim();
						InetAddress clientAdress=dp.getAddress();
						
						//System.out.println("Message recu :   重点观察    \n  "+info);
						// if request for chat, on lui envoi une confirmation de notre cote et lance le server
						if (info.split("/")[0].equals("*chat")) {

							String name = info.split("/")[1];
							String port = info.split("/")[2];
							// distribuer a new port for chatting
							
							int chatport = listeningport + 1000;
							// commencer listening this port

							User u = new User(name,clientAdress,Integer.parseInt(port));
							
							server = new Server(chatport, u, me);
							server.Startlistenning();

							try {

								Session s = new Session(clientAdress,Integer.parseInt(port));
								s.sendmesssage("*re/" + username + "/"+ chatport);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						// receive a request for groupechat
						if (info.split("/")[0].equals("*groupechat")) {
							String name = info.split("/")[1];
							String msg = info.split("/")[2];

							// distribuer a new port for chatting
							//Dialogue dialogue = new Dialogue();
							Graphique.showmessagerecu(name + " send you a Groupe message:\n"
									+ msg);
							// commencer listening this port

						}

						// receive response for chat
						if (info.startsWith("*re")) {
							System.out.println("\n"+"收到聊天请求     准备启动  client");
							String username = info.split("/")[1];
							String chatport = info.split("/")[2];
							// distribuer a new port for chatting
							User u = new User(username,clientAdress,Integer.parseInt(chatport));
							
							
							//u.connecter(Integer.parseInt(chatport));
							
							client = new Client(Integer.parseInt(chatport), u, me,clientAdress);
							client.startClient();

						}
						// if there is new user
						if (info.split("/")[0].equals("newuser")) {
							String name = info.split("/")[1];
							int port = Integer.parseInt(info.split("/")[2]);

							users.add_User(new User(name, dp.getAddress(), port));
							
							Graphique.freshlist();
							
							System.out.println("For  "+username+"端 the user en ligne");
							users.printUserConnecte();
							//Graphique.freshlist();

						}

						if (info.split("/")[0].equals("samename")) {
							// represent this name is already used
							Graphique.showmessage("user alreay exsits!");
						}

						if (info.split("/")[0].equals("sameport")) {
							// represent this name is already used
							Graphique.showmessage("port is alreay used!");

						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		inimsaageThread.start();

	}
	


	// Groupechat message
	public void groupechat(String name, String msg) {
		int destinationport = 0;
		for (User user : users.getUsers()) {
			if (user.get_Name().equals(name)) {
				destinationport = user.get_Port();
			}
		}

		if (destinationport != 0) {
			try {
				Session s = new Session(InetAddress.getByName("localhost"), destinationport);
				s.sendmesssage("*groupechat/" + username + "/" + msg);

			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	
	// Envoyer au destinataire,on va char avec lui  (*chat+author+son port....)
	public void chatWithOne(String nom) {

		int destinationport = 0;
		InetAddress clientAdress = null;
		for (User user : users.getUsers()) {
			if (user.get_Name().equals(nom)) {
				destinationport = user.get_Port();
				clientAdress = user.get_Address();
			}
		}
		
		System.out.println("Test3+++++++++++++++  destination port"+destinationport);
		
		String usernameString = getUserName();
		if (destinationport != 0) {
			Session s;
			//s = new Session(InetAddress.getByName("localhost"), destinationport);
			s = new Session(clientAdress, destinationport);
			s.sendmesssage("*chat/" + usernameString + "/" + listeningport);

		}

	}
	
	
	public static void closeuser() {
		System.out.println("closed");
		running = false;
		try {
			mutiThread.stop();
			inimsaageThread.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public  String getUserName() {
		return username;
	}

	public int getListeningport() {
		return listeningport;
	}

	public List_users getUsers() {
		return users;
	}
	
	public Client getClient() {
		System.out.println("Test11--------------------是否真的  get client");
		while(client==null) {
			
		}
		
		
		
		System.out.println("Test10--------------------是否真的  get client"+client.getPort());
		return client;
	}

	public Server getServer() {
		return server;
	}
	
	
	

}
