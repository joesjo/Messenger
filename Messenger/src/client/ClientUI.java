package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import resources.User;

public class ClientUI extends JPanel {
	
	private ClientController controller;
	
	private JTextField tfIp = new JTextField();
	private JLabel lblIp = new JLabel("IP:");
	private JTextField tfPort = new JTextField();
	private JLabel lblPort = new JLabel("Port:");
	private JTextField tfUsername = new JTextField();
	private JLabel lblUsername = new JLabel("Username:");
	private JButton btnConnect = new JButton("Connect");
	private JButton btnDisconnect = new JButton("Disconnect");
	
	private JTextArea taMessages = new JTextArea();
	private JTextArea taSend = new JTextArea();
	private JButton btnSend = new JButton("Send");
	
	private DefaultListModel friendlist = new DefaultListModel();
	private JList listFriends = new JList(friendlist);
	private JLabel lblFriends = new JLabel("Online Users");
	
	private final int height = 500;
	private final int length = 600;
	
	public ClientUI() {
		setLayout(new BorderLayout());
		JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.add(loginPanel(), BorderLayout.NORTH);
		westPanel.add(messagePanel(), BorderLayout.CENTER);
		add(westPanel, BorderLayout.CENTER);
		add(friendlistPanel(), BorderLayout.EAST);
	}
	
	public void showClient(ClientUI ui) {
		JFrame frame = new JFrame("Login test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(ui);
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel loginPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		panel.add(lblIp);
		tfIp.setPreferredSize(new Dimension(100, 20));
		panel.add(tfIp);
		
		panel.add(lblPort);
		tfPort.setPreferredSize(new Dimension(45, 20));
		panel.add(tfPort);
		
		panel.add(lblUsername);
		tfUsername.setPreferredSize(new Dimension(100, 20));
		panel.add(tfUsername);
		
		btnConnect.setPreferredSize(new Dimension(90, 20));
		panel.add(btnConnect);
		btnConnect.addActionListener(new connectListener());
		
		btnDisconnect.setPreferredSize(new Dimension(100, 20));
		panel.add(btnDisconnect);
		btnDisconnect.addActionListener(new connectListener());
		btnDisconnect.setEnabled(false);
		
		return panel;
	}
	
	private JPanel messagePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		taMessages.setLineWrap(true);
		panel.add(taMessages, BorderLayout.CENTER);
		
		JPanel sendPanel = new JPanel(new BorderLayout());
		
		taSend.setPreferredSize(new Dimension(200, 60));
		taSend.setLineWrap(true);
		sendPanel.add(taSend, BorderLayout.CENTER);
		sendPanel.add(btnSend, BorderLayout.EAST);
		
		panel.add(sendPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel friendlistPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(200, 500));

		lblFriends.setPreferredSize(new Dimension(200, 20));
		panel.add(lblFriends, BorderLayout.NORTH);
		
		panel.add(listFriends, BorderLayout.CENTER);
		
		return panel;
	}
	
	public static void main(String[] args) {
		
	}
	
	public void appendMessage(String message) {
		taMessages.append("\n" + message);
	}
	
	private class connectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnConnect) {
				btnConnect.setEnabled(false);
				try {
					if(controller.connect(tfIp.getText(), Integer.parseInt(tfPort.getText()))) {
						appendMessage("Successfully connected to server!");
						btnDisconnect.setEnabled(true);
					} else {
						appendMessage("Could not connect to server " + tfIp.getText() + ":" + tfPort.getText() + "...");
						btnConnect.setEnabled(true);
					}
				} catch (NumberFormatException e1) {
					appendMessage("Please enter a valid IP and Port...");
					btnConnect.setEnabled(true);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if(e.getSource() == btnDisconnect) {
				btnDisconnect.setEnabled(false);
				if(controller.disconnect()) {
					btnConnect.setEnabled(true);
					appendMessage("Successfully disconnected from server");
				} else {
					btnDisconnect.setEnabled(true);
					appendMessage("Could not disconnect from server");
				}
			}
			
		}
		
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
}
