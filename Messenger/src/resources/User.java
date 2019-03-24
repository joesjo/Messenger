package resources;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ImageIcon icon;
	
	public User(String name, ImageIcon icon) {
		this.name = name;
		this.icon = icon;
	}
	
	public User(String name) {
		this(name, null);
	}

	public String getName() {
		return name;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public boolean equals(User user) {
		User other = user;
		if(this.name.equals(other.getName())) {
			return true;
		}
		return false;
	}

}
