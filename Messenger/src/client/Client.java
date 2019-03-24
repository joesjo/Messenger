package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import resources.User;

public class Client extends Thread {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private User user;
	
	private ClientController controller;

	public Client() {
		
	}

	public void run() {
		while (!socket.isClosed()) {
			try {
				Object obj = ois.readObject();
				if(obj != null) {
					controller.response(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean disconnect() {
		if(!socket.isClosed()) {
			try {
				socket.close();
				System.out.println("Connection shutting down...");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean connect(String ip, int port) throws InterruptedException {
		System.out.println("Connecting to server " + ip + ":" + port + " ... ");
		while(socket == null) {
			try {
				socket = new Socket(ip, port);
				try {
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.print("Could not connect socket streams");
					disconnect();
					return false;
				}
				System.out.print("Successfully connected to server...");
				return true;
			} catch (Exception e) {
				System.out.print("Cannot connect to server");
				return false;
			}
		}
		return false;
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
}
