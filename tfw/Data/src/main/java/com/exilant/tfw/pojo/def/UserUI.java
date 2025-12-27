package com.exilant.tfw.pojo.def;

import java.util.List;

import com.exilant.tfw.pojo.UserRoles;
import com.exilant.tfw.pojo.UsersApplicationMapping;

public class UserUI {
	private static final long serialVersionUID = 1L;

	private int UserID;
	private String Username;
	private String Password;
	private boolean Enabled;
	private String EmailID;
	private int PasswordCount ;
	private List<String> userRoles;
	
	
	public int getUserID() {
		return UserID;
	}


	public void setUserID(int userID) {
		UserID = userID;
	}


	public String getUsername() {
		return Username;
	}


	public void setUsername(String username) {
		Username = username;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public boolean isEnabled() {
		return Enabled;
	}


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


	public List<String> getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}


	@Override
	public String toString() {
		return "UserUI [UserID=" + UserID + ", Username=" + Username
				+ ", Password=" + Password + ", Enabled=" + Enabled
				+ ", EmailID=" + EmailID + ", PasswordCount=" + PasswordCount
				+ ", userRoles=" + userRoles + "]";
	}
}
