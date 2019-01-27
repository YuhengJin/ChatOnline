package Application;

import java.util.ArrayList;



public class List_users {
	
		//Variables
		private ArrayList<User> users;
		private User user_Owner ;
		
		/*public List_users(User user) {
			this.user_Owner = user ;
		}*/
		
		public List_users() {
			users = new ArrayList<User>();
			//this.user_Owner = user;
			//this.users.add(user) ;
		}
		
		//Modification de la liste : Ajout d'un element qui s'est connecté ou deconnection
		public void add_User(User user) {
			this.users.add(user) ;
		}
		
		public void User_DisConnected(User user) {
			this.users.remove(user) ;
		}
		
		
		
		public void printUserConnecte() {
			// TODO Auto-generated method stub

			for(int i=0;i<users.size();i++) {
				System.out.println("*******************");
				System.out.println("["+users.get(i).get_Name()+"]  "+"  IP"+users.get(i).get_Address()+"]"+"  Port "+users.get(i).get_Port()+"]");
			}
		}
		
		public User getUsername() {
			return user_Owner;
		}
		
		
		public ArrayList<User> getUsers() {
			return users;
		}
		



}
