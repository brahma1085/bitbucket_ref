package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.ObjectGroup;

/**
 * The Class Screen.
 * 
 * @author mohammedfirdos
 */
public class Screen implements Serializable {

	/** Screen Entity. */
	private static final long serialVersionUID = 1L;

	/** The screen id. */
	private int screenID;

	/** The screen name. */
	private String screenName;

	/** The description. */
	private String description;

	/** The feature id. */
	private int featureID;

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

	/** The object group. */
	private List<ObjectGroup> objectGroup = new ArrayList<ObjectGroup>(0);

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
	 * @param screenID
	 *            the screenID to set
	 */
	public void setScreenID(int screenID) {
		this.screenID = screenID;
	}

	/**
	 * Gets the screen name.
	 * 
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * Sets the screen name.
	 * 
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the feature id.
	 * 
	 * @return the featureID
	 */
	public int getFeatureID() {
		return featureID;
	}

	/**
	 * Sets the feature id.
	 * 
	 * @param featureID
	 *            the featureID to set
	 */
	public void setFeatureID(int featureID) {
		this.featureID = featureID;
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
	 * @param appID
	 *            the appID to set
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
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param createdDateTime
	 *            the createdDateTime to set
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
	 * @param updatedBy
	 *            the updatedBy to set
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
	 * @param updatedDateTime
	 *            the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the object group.
	 * 
	 * @return the objectGroup
	 */
	public List<ObjectGroup> getObjectGroup() {
		return objectGroup;
	}

	/**
	 * Sets the object group.
	 * 
	 * @param objectGroup
	 *            the objectGroup to set
	 */
	public void setObjectGroup(List<ObjectGroup> objectGroup) {
		this.objectGroup = objectGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Screen [ScreenID=" + screenID + ", ScreenName=" + screenName + ", Description=" + description + ", FeatureID=" + featureID + ", AppID="
				+ appID + "]";
	}

}
