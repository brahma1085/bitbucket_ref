package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.Task;
import com.exilant.tfw.pojo.output.LaneResults;

/**
 * The Class SchedulerLaneDetails.
 * 
 * @author mohammedfirdos
 */
public class SchedulerLaneDetails implements Serializable {

	/** SchedulerLaneDetails Entity. */
	private static final long serialVersionUID = 1L;

	/** The schedule id. */
	private int scheduleID;

	/** The schedule lane id. */
	private int scheduleLaneID;

	/** The agent id. */
	private int agentID;

	/** The lane type. */
	private String laneType;

	/** The lane user name. */
	private String laneUserName;

	/** The runner type. */
	private String runnerType;

	/** The clones. */
	private int clones;

	/** The iterations. */
	private int iterations;

	/** The ramp up delay. */
	private int rampUpDelay;

	/** The duration. */
	private int duration;

	/** The created by. */
	private String createdBy;

	/** The created date time. */
	private Date createdDateTime;

	/** The updated by. */
	private String updatedBy;

	/** The updated date time. */
	private Date updatedDateTime;

	/** The lane results. */
	private List<LaneResults> laneResults = new ArrayList<LaneResults>(0);

	/** The task. */
	private List<Task> task = new ArrayList<Task>(0);

	/**
	 * Gets the schedule id.
	 * 
	 * @return the scheduleID
	 */
	public int getScheduleID() {
		return scheduleID;
	}

	/**
	 * Sets the schedule id.
	 * 
	 * @param scheduleID
	 *            the scheduleID to set
	 */
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	/**
	 * Gets the schedule lane id.
	 * 
	 * @return the scheduleLaneID
	 */
	public int getScheduleLaneID() {
		return scheduleLaneID;
	}

	/**
	 * Sets the schedule lane id.
	 * 
	 * @param scheduleLaneID
	 *            the scheduleLaneID to set
	 */
	public void setScheduleLaneID(int scheduleLaneID) {
		this.scheduleLaneID = scheduleLaneID;
	}

	/**
	 * Gets the agent id.
	 * 
	 * @return the agentID
	 */
	public int getAgentID() {
		return agentID;
	}

	/**
	 * Sets the agent id.
	 * 
	 * @param agentID
	 *            the agentID to set
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * Gets the lane type.
	 * 
	 * @return the laneType
	 */
	public String getLaneType() {
		return laneType;
	}

	/**
	 * Sets the lane type.
	 * 
	 * @param laneType
	 *            the laneType to set
	 */
	public void setLaneType(String laneType) {
		this.laneType = laneType;
	}

	/**
	 * Gets the lane user name.
	 * 
	 * @return the laneUserName
	 */
	public String getLaneUserName() {
		return laneUserName;
	}

	/**
	 * Sets the lane user name.
	 * 
	 * @param laneUserName
	 *            the laneUserName to set
	 */
	public void setLaneUserName(String laneUserName) {
		this.laneUserName = laneUserName;
	}

	/**
	 * Gets the runner type.
	 * 
	 * @return the runnerType
	 */
	public String getRunnerType() {
		return runnerType;
	}

	/**
	 * Sets the runner type.
	 * 
	 * @param runnerType
	 *            the runnerType to set
	 */
	public void setRunnerType(String runnerType) {
		this.runnerType = runnerType;
	}

	/**
	 * Gets the clones.
	 * 
	 * @return the clones
	 */
	public int getClones() {
		return clones;
	}

	/**
	 * Sets the clones.
	 * 
	 * @param clones
	 *            the clones to set
	 */
	public void setClones(int clones) {
		this.clones = clones;
	}

	/**
	 * Gets the iterations.
	 * 
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Sets the iterations.
	 * 
	 * @param iterations
	 *            the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Gets the ramp up delay.
	 * 
	 * @return the rampUpDelay
	 */
	public int getRampUpDelay() {
		return rampUpDelay;
	}

	/**
	 * Sets the ramp up delay.
	 * 
	 * @param rampUpDelay
	 *            the rampUpDelay to set
	 */
	public void setRampUpDelay(int rampUpDelay) {
		this.rampUpDelay = rampUpDelay;
	}

	/**
	 * Gets the duration.
	 * 
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 * 
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
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
	 * Gets the lane results.
	 * 
	 * @return the laneResults
	 */
	public List<LaneResults> getLaneResults() {
		return laneResults;
	}

	/**
	 * Sets the lane results.
	 * 
	 * @param laneResults
	 *            the laneResults to set
	 */
	public void setLaneResults(List<LaneResults> laneResults) {
		this.laneResults = laneResults;
	}

	/**
	 * Gets the task.
	 * 
	 * @return the task
	 */
	public List<Task> getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 * 
	 * @param task
	 *            the task to set
	 */
	public void setTask(List<Task> task) {
		this.task = task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SchedulerLaneDetails [ScheduleID=" + scheduleID + ", ScheduleLaneID=" + scheduleLaneID + ", agentID=" + agentID + ", LaneType=" + laneType
				+ ", LaneUserName=" + laneUserName + ", RunnerType=" + runnerType + ", Clones=" + clones + ", Iterations=" + iterations + ", RampUpDelay="
				+ rampUpDelay + ", Duration=" + duration + ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy="
				+ updatedBy + ", UpdatedDateTime=" + updatedDateTime + "]";
	}

}
