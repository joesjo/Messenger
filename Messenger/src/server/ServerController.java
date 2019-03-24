package server;

import java.util.LinkedList;

import resources.User;

public class ServerController {
	private Server server;
	private LinkedList<User> users = new LinkedList<User>();
	
	public ServerController (Server server) {
		this.server = server;
	}
	
	public ServerController () {
	}
	
	public void response(int clientId, Object obj) {
		if (obj instanceof String) {
			String output = (String)obj;
			server.send(clientId, toUpperCase(output));
		}
		if (obj instanceof User) {
			User user = (User) obj;
			if(addUser(user)) {
				server.send(clientId, user);
			}
		}
	}
	
	private String toUpperCase(String input) {
		return input.toUpperCase();
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	private boolean addUser(User user) {
		if(!users.contains(user)) {
			users.add(user);
			return true;
		}
		
		return false;
	}
}
