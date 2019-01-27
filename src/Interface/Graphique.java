package Interface;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import Application.Chat;
import Application.User;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;

public class Graphique extends JFrame{
	private static JPanel contentPane;
	//Dialogue dialogue;
	static Graphique frame;
	private String pseudo;
	private int port;
	private JButton btnDmarrerChat;
	private JButton btngroupechat;
	private JTextField textField_port;
	private JTextField textField_pass;
	private JTextField textField_name;
	private JTextArea jtaSendMessage;
	private static JTextArea jtaReceivedMessage;
	private JTextField jtfPort;
	private JTextField jtfPseudo;
	private JTextField jtfIP;
	private static DefaultListModel<String> defaultListModel;
	private static JList<String> clientList;
	private static Chat chat;
	private	static String his = "";
	private String typeGra="";
	//private Chat chatGra;
	//private User userGra;
	
	
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Graphique();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Graphique(){
		//Global interface
		setTitle("Imesssager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 530);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		//Panel login
		JPanel panel_login = new JPanel();
		panel_login.setBorder(new EmptyBorder(5, 5, 5, 5));
		//Panel chat		this.chatGra = a;

		final CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		final JPanel panel_chat = new JPanel();

		// Label titre
		JLabel lblBienvenue = new JLabel("Bienvenue");
		lblBienvenue.setForeground(Color.DARK_GRAY);
		lblBienvenue.setFont(new Font("Garuda", Font.BOLD | Font.ITALIC, 20));
		lblBienvenue.setBounds(320, 40, 177, 46);
		//label_title.setLocation(90, y);;
		panel_login.add(lblBienvenue);
		
	
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Bitstream Charter", Font.BOLD, 15));
		lblUsername.setBounds(233, 124, 98, 32);
		
		textField_name = new JTextField();
		textField_name.setBounds(396, 125, 112, 34);
		textField_name.setColumns(10);
		panel_login.setLayout(null);
		panel_login.add(lblUsername);
		panel_login.add(textField_name);
		
		
		
		JLabel label_pass = new JLabel("Password:");
		label_pass.setFont(new Font("Bitstream Charter", Font.BOLD, 15));
        label_pass.setBounds(233,195,80,25);
        panel_login.add(label_pass);
        
        
		JLabel lblNDePort = new JLabel("N° de port:");
		lblNDePort.setFont(new Font("Bitstream Charter", Font.BOLD, 15));
		lblNDePort.setBounds(233, 259, 76, 32);
		panel_login.add(lblNDePort);
		
		JButton button_connect = new JButton("Se connecter");
		button_connect.setFont(new Font("Garuda", Font.BOLD, 15));
		button_connect.setBounds(316, 359, 135, 41);
		panel_login.add(button_connect);
		
		// login
		button_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_name.getText();
				int port = Integer.parseInt(textField_port.getText());

				System.out.println(name);
				System.out.println(port);
				jtfPort.setText(String.valueOf(port));	
				jtfPseudo.setText(name);	
			
				chat = new Chat(name, port);
				System.out.println(name + " is running" + " listeningport " + port);
				chat.initrceivemessage();
				chat.multicast("newuser/" + name + "/" + Integer.toString(port));
				setTitle("User ["+name+"] ");

				// Gerer le pb de Le meme nom, et le port <1024
				//Change the display, entry to the second page
				cardLayout.show(contentPane, "chat");

			}
		});
		
		//No visibal the passeword
		textField_pass = new JPasswordField();
		textField_pass.setBounds(396, 192, 112, 34);
		textField_pass.setColumns(10);
		panel_login.add(textField_pass);
		
		
		textField_port = new JTextField();
		textField_port.setBounds(396, 260, 112, 34);
		textField_port.setColumns(10);
		//Input only number
		textField_port.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
				} else {
					e.consume(); 
				}
			}
		});
		panel_login.add(textField_port);
		
		// when the user close the window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					chat.broadcastmessage("quit*/" + chat.getUserName() + "/" + chat.getListeningport());
				} catch (NullPointerException e1) {
					frame.dispose();
				}
			}
		});
		panel_chat.setLayout(null);
		
		
		
		
		// 设置IPAddress标签
        JLabel jlIP = new JLabel("IPAddress:");
        jlIP.setBounds(30, 15, 70, 25);
        panel_chat.add(jlIP);
        
		// 设置IPAddress显示文本
		jtfIP = new JTextField();
		try {
			jtfIP.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jtfIP.setEditable(false);
		jtfIP.setBounds(118, 15, 187, 25);
		//jtfIP.setText(Common.IP);
		panel_chat.add(jtfIP);

		// 设置Port标签
		JLabel jlPort = new JLabel("Port:");
		jlPort.setBounds(30, 52, 70, 25);
		panel_chat.add(jlPort);
		// 设置Port显示文本
		jtfPort = new JTextField();
		//jtfPort.setText("10055");
		jtfPort.setEditable(false);
		jtfPort.setBounds(118, 50, 187, 25);
		//jtfPort.setText(Common.PORT + "");
		panel_chat.add(jtfPort);
		
		// Pseudo
		JLabel jlPseudo = new JLabel("Pseudo:");
		jlPseudo.setBounds(30, 89, 70, 25);
		panel_chat.add(jlPseudo);
		
		jtfPseudo = new JTextField();
		//jtfPort.setText("10055");
		jtfPseudo.setEditable(false);
		jtfPseudo.setBounds(118, 91, 100, 25);
		//jtfPort.setText(Common.PORT + "");
		panel_chat.add(jtfPseudo);
        
        
        
        
		
		btnDmarrerChat = new JButton("Send Message");
		btnDmarrerChat.setBounds(193, 425, 112, 35);
		panel_chat.add(btnDmarrerChat);
		btnDmarrerChat.setVisible(true);
		
		
		btngroupechat = new JButton("Groupe Chat");
		btngroupechat.setToolTipText("Please choose at least two users");
		btngroupechat.setBounds(43, 425, 112, 35);
		panel_chat.add(btngroupechat);
		btngroupechat.setVisible(true);
		
		
		
		
		
		//La list user enligne
		JLabel labellist = new JLabel("Users en ligne ");
		labellist.setFont(new Font("Dialog", Font.BOLD, 13));
		labellist.setBounds(25, 105, 160, 67);
		panel_chat.add(labellist);
		
		defaultListModel = new DefaultListModel<>();
		clientList = new JList<String>(defaultListModel);
		clientList.setBorder(BorderFactory.createLineBorder(Color.black));
		clientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		clientList.setFocusable(false);

		// Scroll
		JScrollPane jpclientList = new JScrollPane(clientList);
		jpclientList.setBounds(25, 155, 285, 78);
		panel_chat.add(jpclientList);
	
		
		// 设置Send Message标签
        JLabel jlSendMessage = new JLabel("Type Message:");
        jlSendMessage.setFont(new Font("Dialog", Font.BOLD, 13));
        jlSendMessage.setBounds(24, 263, 100, 25);
        panel_chat.add(jlSendMessage);
		// 设置Send Message文本
        jtaSendMessage = new JTextArea();
        jtaSendMessage.setLineWrap(true);
        jtaSendMessage.setWrapStyleWord(true);

        jtaSendMessage.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane jpSendMessage = new JScrollPane(jtaSendMessage);
        jpSendMessage.setBounds(25, 290, 285, 100);
        panel_chat.add(jpSendMessage);
        
        // 设置Received Message标签
        JLabel labelReceivedMessage = new JLabel("Chat Messages:");
        labelReceivedMessage.setFont(new Font("Dialog", Font.BOLD, 13));
        labelReceivedMessage.setBounds(402, 15, 130, 25);
        panel_chat.add(labelReceivedMessage);
		JScrollPane jpReceivedMessage = new JScrollPane();
		jpReceivedMessage.setBounds(400, 50, 300, 380);
		panel_chat.add(jpReceivedMessage);
		// 设置Received Message文本
		jtaReceivedMessage = new JTextArea();
		jpReceivedMessage.setViewportView(jtaReceivedMessage);
		jtaReceivedMessage.setLineWrap(true);
		jtaReceivedMessage.setWrapStyleWord(true);
		jtaReceivedMessage.setEditable(false);
		jtaReceivedMessage.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// groupe chat
		btngroupechat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				ArrayList<String> selectuserList = (ArrayList<String>) clientList.getSelectedValuesList();

				if (selectuserList.size() <= 1) {
					JOptionPane.showMessageDialog(null, "Please select  at least two users to make a groupe discussion!");

				} else {
					String namelist = "";
					for (String name : selectuserList) {
						chat.groupechat(name, jtaSendMessage.getText());
						namelist = namelist+" and "+name;
					}
					
					his = his + chat.getUserName() + " to " + namelist  + ":  "
							+ jtaSendMessage.getText() + "\n";
					jtaReceivedMessage.setText(his);
					
					jtaSendMessage.setText("");

				}

			}
		});
		

		
		// commence un nouveau chat
		btnDmarrerChat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(clientList.getSelectedIndex()==-1) {
					JOptionPane
					.showMessageDialog(null,
							"Warning : Please select at least a user to make a discussion!");
				}
				
				
				
				String name = defaultListModel.getElementAt(clientList.getSelectedIndex());
				
				
				System.out.println("/n Test1  On veut caht avec =================avec qui "+name);
				chat.chatWithOne(name);
				
				Application.User nu = null;
				for (Application.User ou : chat.getUsers().getUsers()) {
					if (ou.get_Name().equals(name)) {
						nu = ou;
					}
				}
				
				
				//String typeString = dialogue.getType();
				//System.out.println("/n Test2================= get the type"+typeString);
				//System.out.println("/n Test2================= get the type"+dialogue.getType());
				
				
				String info = jtaSendMessage.getText();
				his = his + chat.getUserName() + " to " + nu.get_Name() + ":  "
						+ info + "\n";
				System.out.println("/n Test2==================== get message"+his);
				jtaReceivedMessage.setText(his);
				
				
				//chat.getClient().getPortClient();
				//chat.getServer().sendMesFromServer(info);
				
				
				for(int i=0;i<500;i++) {
					System.out.println("CHILL OUT");
				}
				
				//chat.getClient().sendMessage(info);;
				
				/*System.out.println("Test5 ======================"+Dialogue.typeString);
				
				if (Dialogue.typeString.equals("Server")) {
					System.out.println("/n Test4  ================");
					chat.getServer().sendMesFromServer(info);
				} else if (Dialogue.typeString.equals("Client")) {
					System.out.println("Test7=====================CLient send");
					chat.getClient().sendMessage(info);
				}*/
				
				chat.getClient().sendMessage(info);
				jtaSendMessage.setText("");
				
				
				
				/*boolean value = true;
				while (value) {
					System.out.println("/n Test8  =====================");
					if (typeGra.equals("Server")) {
						System.out.println("/n Test4  ================");
						chatGra.getServer().sendMesFromServer(info);
						value = false;
					} else if (typeGra.equals("Client")) {
						System.out.println("Test7=====================CLient send");
						chatGra.getClient().sendMessage(info);
						value = false;
					}else {
						value = true;
					}
				}*/
			}
		});
		
	
		
		//Global
		contentPane.add(panel_login, "login");
		contentPane.add(panel_chat, "chat");
	}
	
	
	public  static void showmessagerecu(String mes) {
		his = his + mes + "\n";
		jtaReceivedMessage.setText(his);
	}
	
	
	public static void showmessage(String mes) {
		if(mes.equals("user alreay exsits!")) {
			JOptionPane
			.showMessageDialog(null,
					"Warning : Please select availible username!");
			frame.setTitle("User OFFLINE ");
			chat.closeuser();
		}else {
			JOptionPane
			.showMessageDialog(null,
					"Warning : Please select a availible port!");
			frame.setTitle("User OFFLINE ");
			chat.closeuser();
		}
		try {
			chat.broadcastmessage("quit*/" + chat.getUserName() + "/" + chat.getListeningport());
		} catch (NullPointerException e1) {
			frame.dispose();
		}
	}
	

	
	
	
	
	
	public static void freshlist() {
		//defaultListModel.clear();
		
		System.out.println("******Try to freshlist dans l'interface******");
		
		ArrayList<String> usernamelist = new ArrayList<String>();
		//chat.getUsers().printUserConnecte();
		
		for (Application.User u : chat.getUsers().getUsers()) {
			usernamelist.add(u.get_Name());
		}
		defaultListModel.clear();
		for (String element : usernamelist) {
			defaultListModel.addElement(element);
		}

	}


	public JButton get_btnDmarrerChat() {
		return this.btnDmarrerChat;
	}
	
	
	public JTextArea get_jtaSendMessage() {
		return this.jtaSendMessage;
	}
	
	public JTextArea get_jtaReceivedMessage() {
		return this.jtaReceivedMessage;
	}
	
	
	public JPanel get_contentPane() {
		return this.contentPane;
	}
	
	
	
	
	
	
	
	
	
	

}
	
	


