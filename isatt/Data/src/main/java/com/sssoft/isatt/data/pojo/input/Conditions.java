package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Conditions.
 *
 * @author mohammedfirdos
 */
public class Conditions implements Serializable {

	/** Conditions Entity. */
	private static final long serialVersionUID = 1L;

	/** The condition id. */
	private int conditionID;
	
	/** The condition name. */
	private String conditionName;
	
	/** The description. */
	private String description;
	
	/** The expression. */
	private String expression;
	
	/** The condition group id. */
	private int conditionGroupID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The test condition data. */
	private List<TestConditionData> testConditionData = new ArrayList<TestConditionData>(0);

	/**
	 * Gets the condition id.
	 *
	 * @return the conditionID
	 */
	public int getConditionID() {
		return conditionID;
	}

	/**
	 * Sets the condition id.
	 *
	 * @param conditionID the conditionID to set
	 */
	public void setConditionID(int conditionID) {
		this.conditionID = conditionID;
	}

	/**
	 * Gets the condition name.
	 *
	 * @return the conditionName
	 */
	public String getConditionName() {
		return conditionName;
	}

	/**
	 * Sets the condition name.
	 *
	 * @param conditionName the conditionName to set
	 */
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
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
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression.
	 *
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Gets the condition group id.
	 *
	 * @return the conditionGroupID
	 */
	public int getConditionGroupID() {
		return conditionGroupID;
	}

	/**
	 * Sets the condition group id.
	 *
	 * @param conditionGroupID the conditionGroupID to set
	 */
	public void setConditionGroupID(int conditionGroupID) {
		this.conditionGroupID = conditionGroupID;
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
	 * Gets the test condition data.
	 *
	 * @return the testConditionData
	 */
	public List<TestConditionData> getTestConditionData() {
		return testConditionData;
	}

	/**
	 * Sets the test condition data.
	 *
	 * @param testConditionData the testConditionData to set
	 */
	public void setTestConditionData(List<TestConditionData> testConditionData) {
		this.testConditionData = testConditionData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Conditions [ConditionID=" + conditionID + ", ConditionName=" + conditionName + ", Description=" + description + ", Expression="
				+ expression + ", ConditionGroupID=" + conditionGroupID + "]";
	}

}
