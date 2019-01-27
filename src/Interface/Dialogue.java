/*package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Application.Chat;
import Application.User;


public class Dialogue {
	
	public Chat chatDia;
	public static User ouDia;
	private	String his = "";
	private Graphique graphique;
	
	public static String typeString = "";
	
	
	
	public Dialogue(Chat u1, User u2, String type) {

		chatDia = u1;
		ouDia = u2;
		typeString = type;
		
		graphique =new Graphique(u1,u2,type);
		
		
		
		
		
		//System.out.println("Test 5=========================a new Dialogue  type is "+type);
		//System.out.println("Test6=============================typeGra is "+graphique.getTypeGra());
		
		//setTitle(chat.getUserName() + " chatting with " + ou.get_Name());
		
		
		graphique.get_btnDmarrerChat().addActionListener(new ActionListener() {

	
			public void actionPerformed(ActionEvent e) {
				String info = graphique.get_jtaSendMessage().getText();
				his = his + chat.getUserName() + " to " + ou.get_Name() + ":  "
						+ info + "\n";
				graphique.get_jtaReceivedMessage().setText(his);
				if (typeString.equals("Server")) {
					chat.getServer().sendMesFromServer(info);
				} else if (typeString.equals("Client")) {
					chat.getClient().sendMessage(info);
				}

			}
		});	
		
	}
	
	
		
		
	
	
	
	public  void showmessagerecu(String mes) {
		his = his + mes + "\n";
		Graphique.frame.get_jtaReceivedMessage().setText(his);
	}
	
	
	public  Dialogue() {
		// TODO Auto-generated constructor stub
	}
	
//	public  static void  showmessageialog(String msg){
//		
//		JOptionPane.showMessageDialog(Graphique., msg);
//		
//	}
	
	
}




*/