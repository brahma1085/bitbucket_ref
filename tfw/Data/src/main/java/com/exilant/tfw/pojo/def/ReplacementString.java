package com.exilant.tfw.pojo.def;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class ReplacementString.
 */
public class ReplacementString implements Serializable {

	/** Default serial version id. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private BigDecimal id;
	
	/** The value. */
	private String value;
	
	/** The level. */
	private String level;
	
	/** The foreign id. */
	private String foreignId;
	
	/** The app name. */
	private String appName;
	
	/** The encrypted. */
	private int encrypted;
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new replacement string.
	 */
	public ReplacementString() {
		super();
	}

	/**
	 * Instantiates a new replacement string.
	 *
	 * @param id the id
	 * @param value the value
	 * @param level the level
	 * @param foreignId the foreign id
	 * @param appName the app name
	 * @param encrypted the encrypted
	 * @param name the name
	 */
	public ReplacementString(BigDecimal id, String value, String level, String foreignId, String appName, int encrypted, String name) {
		super();
		this.id = id;
		this.value = value;
		this.level = level;
		this.foreignId = foreignId;
		this.appName = appName;
		this.encrypted = encrypted;
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(BigDecimal id) {
		this.id = id;
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
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @param level the new level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Gets the foreign id.
	 *
	 * @return the foreign id
	 */
	public String getForeignId() {
		return foreignId;
	}

	/**
	 * Sets the foreign id.
	 *
	 * @param foreignId the new foreign id
	 */
	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	/**
	 * Gets the app name.
	 *
	 * @return the app name
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Sets the app name.
	 *
	 * @param appName the new app name
	 */
	public void setAppName(String appName) {
		this.appName = appName;
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
	 * @param encrypted the new encrypted
	 */
	public void setEncrypted(int encrypted) {
		this.encrypted = encrypted;
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReplacementString [id=" + id + ", value=" + value + ", level=" + level + ", foreignId=" + foreignId + ", appName=" + appName
				+ ", encrypted=" + encrypted + ", name=" + name + "]";
	}

}
