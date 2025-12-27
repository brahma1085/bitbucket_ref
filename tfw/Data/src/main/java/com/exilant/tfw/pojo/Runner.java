package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class Runner.
 *
 * @author mohammedfirdos
 */
public class Runner implements Serializable {

	/** Runner Entity. */
	private static final long serialVersionUID = 1L;

	/** The runner id. */
	private int runnerID;
	
	/** The runner name. */
	private String runnerName;
	
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

	/**
	 * Gets the runner id.
	 *
	 * @return the runnerID
	 */
	public int getRunnerID() {
		return runnerID;
	}

	/**
	 * Sets the runner id.
	 *
	 * @param runnerID the runnerID to set
	 */
	public void setRunnerID(int runnerID) {
		this.runnerID = runnerID;
	}

	/**
	 * Gets the runner name.
	 *
	 * @return the runnerName
	 */
	public String getRunnerName() {
		return runnerName;
	}

	/**
	 * Sets the runner name.
	 *
	 * @param runnerName the runnerName to set
	 */
	public void setRunnerName(String runnerName) {
		this.runnerName = runnerName;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Runner [RunnerID=" + runnerID + ", RunnerName=" + runnerName + ", Description=" + description + "]";
	}

}
