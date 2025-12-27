package com.exilant.tfw.pojo;

import java.io.Serializable;

/**
 * The Class GenericData.
 *
 * @author mohammedfirdos
 */
public class GenericData implements Serializable {

	/** GenericData Entity. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private int id;
	
	/** The key name. */
	private String keyName;
	
	/** The value. */
	private String value;
	
	/** The app id. */
	private int appID;

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
		this.id = iD;
	}

	/**
	 * Gets the key name.
	 *
	 * @return the key
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * Sets the key name.
	 *
	 * @param keyName the new key name
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GenericData [ID=" + id + ", KeyName=" + keyName + ", Value=" + value + ", AppID=" + appID + "]";
	}
}
