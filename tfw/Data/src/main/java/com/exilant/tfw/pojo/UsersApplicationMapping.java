/**
 * 
 */
package com.exilant.tfw.pojo;

import java.io.Serializable;

/**
 * @author mohammedfirdos
 * 
 */
public class UsersApplicationMapping implements Serializable {

	/**
	 * UsersApplicationMapping Entity
	 */
	private static final long serialVersionUID = 1L;

	private int UsersApplicationMappingID;
	private int UserID;
	private int AppID;
	private String Authority;
	
	public UsersApplicationMapping() {
		super();
	}

	/**
	 * @return the usersApplicationMappingID
	 */
	public int getUsersApplicationMappingID() {
		return UsersApplicationMappingID;
	}

	/**
	 * @param usersApplicationMappingID the usersApplicationMappingID to set
	 */
	public void setUsersApplicationMappingID(int usersApplicationMappingID) {
		UsersApplicationMappingID = usersApplicationMappingID;
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
	 * @return the appID
	 */
	public int getAppID() {
		return AppID;
	}

	/**
	 * @param appID the appID to set
	 */
	public void setAppID(int appID) {
		AppID = appID;
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
		return "UsersApplicationMapping [UsersApplicationMappingID=" + UsersApplicationMappingID + ", UserID=" + UserID + ", AppID=" + AppID
				+ ", Authority=" + Authority + "]";
	}
	
}
