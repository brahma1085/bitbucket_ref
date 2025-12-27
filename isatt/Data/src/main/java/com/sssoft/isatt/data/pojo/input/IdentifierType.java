package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class IdentifierType.
 *
 * @author mohammedfirdos
 */
public class IdentifierType implements Serializable {

	/** IdentifierType Entity. */
	private static final long serialVersionUID = 1L;

	/** The identifier type id. */
	private int identifierTypeID;
	
	/** The identifier type name. */
	private String identifierTypeName;
	
	/** The description. */
	private String description;
	
	/** The app id. */
	private int appID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The objects. */
	private List<Objects> objects = new ArrayList<Objects>(0);

	/**
	 * Gets the identifier type id.
	 *
	 * @return the identifierTypeID
	 */
	public int getIdentifierTypeID() {
		return identifierTypeID;
	}

	/**
	 * Sets the identifier type id.
	 *
	 * @param identifierTypeID the identifierTypeID to set
	 */
	public void setIdentifierTypeID(int identifierTypeID) {
		this.identifierTypeID = identifierTypeID;
	}

	/**
	 * Gets the identifier type name.
	 *
	 * @return the identifierTypeName
	 */
	public String getIdentifierTypeName() {
		return identifierTypeName;
	}

	/**
	 * Sets the identifier type name.
	 *
	 * @param identifierTypeName the identifierTypeName to set
	 */
	public void setIdentifierTypeName(String identifierTypeName) {
		this.identifierTypeName = identifierTypeName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the created by.
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date time.
	 *
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Sets the created date time.
	 *
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date time.
	 *
	 * @return the updatedDateTime
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * Sets the updated date time.
	 *
	 * @param updatedDateTime the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public List<Objects> getObjects() {
		return objects;
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects the objects to set
	 */
	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IdentifierType [IdentifierTypeID=" + identifierTypeID + ", IdentifierTypeName=" + identifierTypeName + ", Description=" + description
				+ ", AppID=" + appID + "]";
	}

}
