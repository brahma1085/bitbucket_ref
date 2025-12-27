package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Objects.
 *
 * @author mohammedfirdos
 */
public class Objects implements Serializable {

	/** Objects Entity. */
	private static final long serialVersionUID = 1L;

	/** The object id. */
	private int objectID;
	
	/** The object name. */
	private String objectName;
	
	/** The description. */
	private String description;
	
	/** The object group id. */
	private int objectGroupID;
	
	/** The object type id. */
	private int objectTypeID;
	
	/** The identifier type id. */
	private int identifierTypeID;
	
	/** The app id. */
	private int appID;
	
	/** The identifier. */
	private String identifier;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The param. */
	private List<Param> param = new ArrayList<Param>(0);
	
	/** The identifier type. */
	private IdentifierType identifierType;
	
	/** The identifier type value. */
	private String identifierTypeValue;
	
	/** The object group value. */
	private String objectGroupValue;
	
	/** The screen value. */
	private String screenValue;
	
	/** The object type. */
	private ObjectType objectType;

	/**
	 * Gets the object type.
	 *
	 * @return the object type
	 */
	public ObjectType getObjectType() {
		return objectType;
	}

	/**
	 * Sets the object type.
	 *
	 * @param objectType the new object type
	 */
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	/**
	 * Gets the identifier type.
	 *
	 * @return the identifier type
	 */
	public IdentifierType getIdentifierType() {
		return identifierType;
	}

	/**
	 * Sets the identifier type.
	 *
	 * @param identifierType the new identifier type
	 */
	public void setIdentifierType(IdentifierType identifierType) {
		this.identifierType = identifierType;
	}

	/**
	 * Gets the object id.
	 *
	 * @return the objectID
	 */
	public int getObjectID() {
		return objectID;
	}

	/**
	 * Sets the object id.
	 *
	 * @param objectID the objectID to set
	 */
	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	/**
	 * Gets the object name.
	 *
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * Sets the object name.
	 *
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
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
	 * Gets the object group id.
	 *
	 * @return the objectGroupID
	 */
	public int getObjectGroupID() {
		return objectGroupID;
	}

	/**
	 * Sets the object group id.
	 *
	 * @param objectGroupID the objectGroupID to set
	 */
	public void setObjectGroupID(int objectGroupID) {
		this.objectGroupID = objectGroupID;
	}

	/**
	 * Gets the object type id.
	 *
	 * @return the objectTypeID
	 */
	public int getObjectTypeID() {
		return objectTypeID;
	}

	/**
	 * Sets the object type id.
	 *
	 * @param objectTypeID the objectTypeID to set
	 */
	public void setObjectTypeID(int objectTypeID) {
		this.objectTypeID = objectTypeID;
	}

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
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
	 * Gets the param.
	 *
	 * @return the param
	 */
	public List<Param> getParam() {
		return param;
	}

	/**
	 * Sets the param.
	 *
	 * @param param the param to set
	 */
	public void setParam(List<Param> param) {
		this.param = param;
	}

	/**
	 * Gets the identifier type value.
	 *
	 * @return the identifier type value
	 */
	public String getIdentifierTypeValue() {
		return identifierTypeValue;
	}

	/**
	 * Sets the identifier type value.
	 *
	 * @param identifierTypeValue the new identifier type value
	 */
	public void setIdentifierTypeValue(String identifierTypeValue) {
		this.identifierTypeValue = identifierTypeValue;
	}

	/**
	 * Gets the object group value.
	 *
	 * @return the object group value
	 */
	public String getObjectGroupValue() {
		return objectGroupValue;
	}

	/**
	 * Sets the object group value.
	 *
	 * @param objectGroupValue the new object group value
	 */
	public void setObjectGroupValue(String objectGroupValue) {
		this.objectGroupValue = objectGroupValue;
	}

	/**
	 * Gets the screen value.
	 *
	 * @return the screen value
	 */
	public String getScreenValue() {
		return screenValue;
	}

	/**
	 * Sets the screen value.
	 *
	 * @param screenValue the new screen value
	 */
	public void setScreenValue(String screenValue) {
		this.screenValue = screenValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Objects [ObjectID=" + objectID + ", ObjectName=" + objectName + ", Description=" + description + ", ObjectGroupID=" + objectGroupID
				+ ", ObjectTypeID=" + objectTypeID + ", IdentifierTypeID=" + identifierTypeID + ", AppID=" + appID + ", Identifier=" + identifier + "]";
	}

}
