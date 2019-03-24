package client;

import javax.swing.JFrame;

import resources.User;

public class ClientController {
	
	private Client clientconnector;
	private ClientUI clientui;
	
	public ClientController(Client clientconnector, ClientUI clientui) {
		this.clientconnector = clientconnector;
		this.clientui = clientui;
		clientconnector.setController(this);
		clientui.setController(this);
		clientui.showClient(clientui);
	}
	
	public static void main(String[] args) {
		ClientController controller = new ClientController(new Client(), new ClientUI());
	}
	
	public boolean connect(String ip, int port) throws InterruptedException {
		return clientconnector.connect(ip, port);
	}
	
	public boolean disconnect() {
		return clientconnector.disconnect();
	}
	
	public void response(Object obj) {
		
	}
}
