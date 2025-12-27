package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ParamGroup.
 *
 * @author mohammedfirdos
 */
public class ParamGroup implements Serializable {

	/** ParamGroup Entity. */
	private static final long serialVersionUID = 1L;

	/** The param group id. */
	private int paramGroupID;
	
	/** The param group name. */
	private String paramGroupName;
	
	/** The description. */
	private String description;
	
	/** The tag. */
	private String tag;
	
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
	
	/** The object group id. */
	private int objectGroupID;
	
	/** The param. */
	private List<Param> param = new ArrayList<Param>(0);
	
	/** The test param data. */
	private List<TestParamData> testParamData = new ArrayList<TestParamData>(0);
	
	/** The object group. */
	private ObjectGroup objectGroup;
	
	/** The param group. */
	private List<ParamGroup> paramGroup = new ArrayList<ParamGroup>(0);

	/**
	 * Gets the param group.
	 *
	 * @return the param group
	 */
	public List<ParamGroup> getParamGroup() {
		return paramGroup;
	}

	/**
	 * Sets the param group.
	 *
	 * @param paramGroup the new param group
	 */
	public void setParamGroup(List<ParamGroup> paramGroup) {
		this.paramGroup = paramGroup;
	}

	/**
	 * Gets the object group.
	 *
	 * @return the object group
	 */
	public ObjectGroup getObjectGroup() {
		return objectGroup;
	}

	/**
	 * Sets the object group.
	 *
	 * @param objectGroup the new object group
	 */
	public void setObjectGroup(ObjectGroup objectGroup) {
		this.objectGroup = objectGroup;
	}

	/**
	 * Gets the param group id.
	 *
	 * @return the paramGroupID
	 */
	public int getParamGroupID() {
		return paramGroupID;
	}

	/**
	 * Sets the param group id.
	 *
	 * @param paramGroupID the paramGroupID to set
	 */
	public void setParamGroupID(int paramGroupID) {
		this.paramGroupID = paramGroupID;
	}

	/**
	 * Gets the param group name.
	 *
	 * @return the paramGroupName
	 */
	public String getParamGroupName() {
		return paramGroupName;
	}

	/**
	 * Sets the param group name.
	 *
	 * @param paramGroupName the paramGroupName to set
	 */
	public void setParamGroupName(String paramGroupName) {
		this.paramGroupName = paramGroupName;
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
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
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
	 * Gets the test param data.
	 *
	 * @return the testParamData
	 */
	public List<TestParamData> getTestParamData() {
		return testParamData;
	}

	/**
	 * Sets the test param data.
	 *
	 * @param testParamData the testParamData to set
	 */
	public void setTestParamData(List<TestParamData> testParamData) {
		this.testParamData = testParamData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParamGroup [ParamGroupID=" + paramGroupID + ", ParamGroupName=" + paramGroupName + ", Description=" + description + ", Tag=" + tag
				+ ", AppID=" + appID + ", ObjectGroupID=" + objectGroupID + "]";
	}

}
