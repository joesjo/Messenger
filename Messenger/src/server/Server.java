package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

import resources.User;

public class Server implements Runnable {
	private ServerSocket serversocket;
	private Thread server = new Thread(this);
	private ClientHandler tempClient;
	private LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
	private ServerController controller;

	public Server(int port, ServerController controller) throws IOException {
		this.controller = controller;
		controller.setServer(this);
		serversocket = new ServerSocket(port);
		server.start();
	}

	public void run() {
		System.out.println("MessengerServer running, port " + serversocket.getLocalPort());
		while(true) {
			try  {
				Socket socket = serversocket.accept();
				tempClient = new ClientHandler(socket);
				tempClient.start();
				clients.add(tempClient);
			} catch(IOException e) { 
			}
		}
	}
	
	public class ClientHandler extends Thread {
		
		private Socket socket;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(clients.contains(this))
					clients.remove(this);
			}
		}
		
		public void run() {
			while(!socket.isClosed()) {
				try {
					Object input = ois.readObject();
					System.out.println("Received object: " + input.toString() + " from " + socket.getInetAddress());
					controller.response(clients.indexOf(this), input);
				} catch (Exception e) {
				}
			}
			try {
				socket.close();
				if(clients.contains(this))
					clients.remove(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		private void respond(String obj) {
			try {
				oos.writeUTF(obj);
				oos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void respond(User obj) {
			try {
				oos.writeObject(obj);
				oos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send(int id, String string) {
		clients.get(id).respond(string);
	}
	
	public void send(int id, User user) {
		clients.get(id).respond(user);
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server(1433, new ServerController());
	}

}
