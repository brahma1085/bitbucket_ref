package com.myeclipseide.examples.login.jsf;

public final class UserBean extends Object {

	private String password;

	private String userName;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String loginUser() {
		if ("myeclipse".equalsIgnoreCase(getUserName())
				&& "myeclipse".equalsIgnoreCase(getPassword()))
			return "success";

		return "failure";
	}
}
