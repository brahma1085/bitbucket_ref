/**
 * 
 */
package com.sssoft.isatt.data.pojo;

import java.io.Serializable;

/**
 * @author mohammedfirdos
 * 
 */
public class Users implements Serializable {

	/**
	 * Users Entity
	 */
	private static final long serialVersionUID = 1L;

	private int UserID;
	private String Username;
	private String Password;
	private boolean Enabled;
	private String EmailID;
	private int PasswordCount ;
	
	public Users() {
		super();
	}
	
	/**
	 * @return the uSERID
	 */
	public int getUserID() {
		return UserID;
	}
	
	/**
	 * @param uSERID the uSERID to set
	 */
	public void setUserID(int UserID) {
		this.UserID = UserID;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}
	
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return Enabled;
	}
	
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}
	
	public String getEmailID() {
		return EmailID;
	}

	public void setEmailID(String emailID) {
		EmailID = emailID;
	}

	public int getPasswordCount() {
		return PasswordCount;
	}

	public void setPasswordCount(int passwordCount) {
		PasswordCount = passwordCount;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [USERID=" + UserID + ", Username=" + Username
				+ ", Password=" + Password + ", Enabled=" + Enabled
				+ ", EmailID=" + EmailID + ", PasswordCount=" + PasswordCount
				+ "]";
	}

}