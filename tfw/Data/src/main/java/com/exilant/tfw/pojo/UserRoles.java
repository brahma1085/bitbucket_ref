/**
 * 
 */
package com.exilant.tfw.pojo;

import java.io.Serializable;

/**
 * @author mohammedfirdos
 * 
 */
public class UserRoles implements Serializable {

	/**
	 * Users Entity
	 */
	private static final long serialVersionUID = 1L;

	private int UserRolesID;
	private int UserID;
	private String Authority;
	
	public UserRoles() {
		super();
	}

	/**
	 * @return the userRolesID
	 */
	public int getUserRolesID() {
		return UserRolesID;
	}

	/**
	 * @param userRolesID the userRolesID to set
	 */
	public void setUserRolesID(int userRolesID) {
		UserRolesID = userRolesID;
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
	 * @return the authority
	 */
	public String getAuthority() {
		return Authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		Authority = authority;
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
		return "UserRoles [UserRolesID=" + UserRolesID + ", UserID=" + UserID + ", Authority=" + Authority + "]";
	}

}
