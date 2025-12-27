package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.ObjectType;
import com.exilant.tfw.pojo.input.TestStep;

/**
 * The Class Actions.
 * 
 * @author mohammedfirdos
 * 
 *         Actions Entity.
 */
public class Actions implements Serializable {

	/** Default serial version id. */
	private static final long serialVersionUID = 1L;

	/** The action id. */
	private int actionID;

	/** The action name. */
	private String actionName;

	/** The description. */
	private String description;

	/** The created by. */
	private String createdBy;

	/** The created date time. */
	private Date createdDateTime;

	/** The updated by. */
	private String updatedBy;

	/** The updated date time. */
	private Date updatedDateTime;

	/** The object type. */
	private List<ObjectType> objectType = new ArrayList<ObjectType>(0);

	/** The test step. */
	private List<TestStep> testStep = new ArrayList<TestStep>(0);

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
	 * @param actionID
	 *            the actionID to set
	 */
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}

	/**
	 * Gets the action name.
	 * 
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * Sets the action name.
	 * 
	 * @param actionName
	 *            the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
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
	 * Gets the object type.
	 * 
	 * @return the objectType
	 */
	public List<ObjectType> getObjectType() {
		return objectType;
	}

	/**
	 * Sets the object type.
	 * 
	 * @param objectType
	 *            the objectType to set
	 */
	public void setObjectType(List<ObjectType> objectType) {
		this.objectType = objectType;
	}

	/**
	 * Gets the test step.
	 * 
	 * @return the testStep
	 */
	public List<TestStep> getTestStep() {
		return testStep;
	}

	/**
	 * Sets the test step.
	 * 
	 * @param testStep
	 *            the testStep to set
	 */
	public void setTestStep(List<TestStep> testStep) {
		this.testStep = testStep;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Actions [ActionID=" + actionID + ", ActionName=" + actionName + ", Description=" + description + "]";
	}

}
