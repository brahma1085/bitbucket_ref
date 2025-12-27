package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ObjectType.
 *
 * @author mohammedfirdos
 */
public class ObjectType implements Serializable {

	/** ObjectType Entity. */
	private static final long serialVersionUID = 1L;

	/** The object type id. */
	private int objectTypeID;
	
	/** The object type name. */
	private String objectTypeName;
	
	/** The description. */
	private String description;
	
	/** The action id. */
	private int actionID;
	
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
	 * Gets the object type name.
	 *
	 * @return the objectTypeName
	 */
	public String getObjectTypeName() {
		return objectTypeName;
	}

	/**
	 * Sets the object type name.
	 *
	 * @param objectTypeName the objectTypeName to set
	 */
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
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
	 * Gets the action id.
	 *
	 * @return the actionID
	 */
	public int getActionID() {
		return actionID;
	}

	/**
	 * Sets the action id.
	 *
	 * @param actionID the actionID to set
	 */
	public void setActionID(int actionID) {
		this.actionID = actionID;
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
		return "ObjectType [ObjectTypeID=" + objectTypeID + ", ObjectTypeName=" + objectTypeName + ", Description=" + description + ", ActionID="
				+ actionID + "]";
	}

}
