/**
 * 
 */
package com.exilant.tfw.pojo;

import java.io.Serializable;


/**
 * @author mohammedfirdos
 * 
 */
public class Roles implements Serializable {

	/**
	 * Roles Entity
	 */
	private static final long serialVersionUID = 1L;

	private int RoleID;
	private String Authority;
	private String RolesDescription;
	
	public Roles() {
		super();
	}
	
	/**
	 * @return the rOLEID
	 */
	
	public int getRoleID() {
		return RoleID;
	}
	
	/**
	 * @param rOLEID the rOLEID to set
	 */
	public void setRoleID(int RoleID) {
		this.RoleID = RoleID;
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
	 * @return the rolesDescription
	 */
	public String getRolesDescription() {
		return RolesDescription;
	}

	/**
	 * @param rolesDescription the rolesDescription to set
	 */
	public void setRolesDescription(String rolesDescription) {
		RolesDescription = rolesDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Roles [ROLEID=" + RoleID + ", Authority=" + Authority + ", RolesDescription=" + RolesDescription + "]";
	}
	
}