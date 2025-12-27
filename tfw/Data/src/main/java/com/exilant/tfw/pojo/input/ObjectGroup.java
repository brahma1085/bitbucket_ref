package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ObjectGroup.
 *
 * @author mohammedfirdos
 */
public class ObjectGroup implements Serializable {

	/** ObjectGroup Entity. */
	private static final long serialVersionUID = 1L;

	/** The object group id. */
	private int objectGroupID;
	
	/** The object group name. */
	private String objectGroupName;
	
	/** The description. */
	private String description;
	
	/** The app id. */
	private int appID;
	
	/** The screen id. */
	private int screenID;
	
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
	
	/** The param group. */
	private List<ParamGroup> paramGroup = new ArrayList<ParamGroup>(0);
	
	/** The screen name. */
	private String screenName;

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
	 * @return the int
	 */
	public int setObjectGroupID(int objectGroupID) {
		return this.objectGroupID = objectGroupID;
	}

	/**
	 * Gets the object group name.
	 *
	 * @return the objectGroupName
	 */
	public String getObjectGroupName() {
		return objectGroupName;
	}

	/**
	 * Sets the object group name.
	 *
	 * @param objectGroupName the objectGroupName to set
	 */
	public void setObjectGroupName(String objectGroupName) {
		this.objectGroupName = objectGroupName;
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
	 * Gets the screen id.
	 *
	 * @return the screenID
	 */
	public int getScreenID() {
		return screenID;
	}

	/**
	 * Sets the screen id.
	 *
	 * @param screenID the screenID to set
	 */
	public void setScreenID(int screenID) {
		this.screenID = screenID;
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

	/**
	 * Gets the param group.
	 *
	 * @return the paramGroup
	 */
	public List<ParamGroup> getParamGroup() {
		return paramGroup;
	}

	/**
	 * Sets the param group.
	 *
	 * @param paramGroup the paramGroup to set
	 */
	public void setParamGroup(List<ParamGroup> paramGroup) {
		this.paramGroup = paramGroup;
	}

	/**
	 * Gets the screen name.
	 *
	 * @return the screen name
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * Sets the screen name.
	 *
	 * @param screenName the new screen name
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ObjectGroup [ObjectGroupID=" + objectGroupID + ", ObjectGroupName=" + objectGroupName + ", Description=" + description + ", AppID="
				+ appID + ", ScreenID=" + screenID + "]";
	}

}
