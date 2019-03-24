package resources;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User sender;
	private User receiver;
	private String message;
	private DateTime timeSent;
	
	public Message(User sender, User receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.timeSent = new DateTime();
	}
	
	public String toString() {
		return timeSent.getHourOfDay() + ":" + timeSent.getMinuteOfHour() + " " + sender.getName() + ": " + this.message;
	}
	
	public User getSender() {
		return sender;
	}
	
	public User getReceiver() {
		return receiver;
	}
}