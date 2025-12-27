package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;

/**
 * The Class ReplacementStrings.
 *
 * @author mohammedfirdos
 */
public class ReplacementStrings implements Serializable {

	/** Replacement_Strings Entity. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private int id;
	
	/** The app id. */
	private int appID;
	
	/** The level. */
	private String level;
	
	/** The foreign id. */
	private String foreignID;
	
	/** The name. */
	private String name;
	
	/** The value. */
	private String value;
	
	/** The encrypted. */
	private int encrypted;

	/**
	 * Gets the id.
	 *
	 * @return the iD
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		id = iD;
	}

	/**
	 * Gets the app id.
	 *
	 * @return the appID
	 */
	public int getAppID() {
		return appID;
	}

	/**
	 * Sets the app id.
	 *
	 * @param appID the appID to set
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Gets the foreign id.
	 *
	 * @return the foreign_ID
	 */
	public String getForeignID() {
		return foreignID;
	}

	/**
	 * Sets the foreign id.
	 *
	 * @param foreignID the new foreign id
	 */
	public void setForeignID(String foreignID) {
		this.foreignID = foreignID;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the encrypted.
	 *
	 * @return the encrypted
	 */
	public int getEncrypted() {
		return encrypted;
	}

	/**
	 * Sets the encrypted.
	 *
	 * @param encrypted the encrypted to set
	 */
	public void setEncrypted(int encrypted) {
		this.encrypted = encrypted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReplacementStrings [ID=" + id + ", AppID=" + appID + ", Level=" + level + ", Foreign_ID=" + foreignID + ", Name=" + name + ", Value="
				+ value + ", Encrypted=" + encrypted + "]";
	}

}
