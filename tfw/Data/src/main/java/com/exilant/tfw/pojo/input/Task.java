package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.output.TaskResult;

/**
 * The Class Task.
 *
 * @author mohammedfirdos
 */
public class Task implements Serializable {

	/** Task Entity. */
	private static final long serialVersionUID = 1L;

	/** The task id. */
	private int taskID;
	
	/** The lane id. */
	private int laneID;
	
	/** The task name. */
	private String taskName;
	
	/** The test plan xls path. */
	private String testPlanXlsPath;
	
	/** The data set. */
	private String dataSet;
	
	/** The repeat no. */
	private int repeatNo;
	
	/** The tags to run. */
	private String tagsToRun;
	
	/** The task result. */
	private List<TaskResult> taskResult = new ArrayList<TaskResult>(0);
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;

	/**
	 * Gets the task id.
	 *
	 * @return the taskID
	 */
	public int getTaskID() {
		return taskID;
	}

	/**
	 * Sets the task id.
	 *
	 * @param taskID the taskID to set
	 */
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	/**
	 * Gets the lane id.
	 *
	 * @return the laneID
	 */
	public int getLaneID() {
		return laneID;
	}

	/**
	 * Sets the lane id.
	 *
	 * @param laneID the laneID to set
	 */
	public void setLaneID(int laneID) {
		this.laneID = laneID;
	}

	/**
	 * Gets the task name.
	 *
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the task name.
	 *
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Gets the test plan xls path.
	 *
	 * @return the testPlanXlsPath
	 */
	public String getTestPlanXlsPath() {
		return testPlanXlsPath;
	}

	/**
	 * Sets the test plan xls path.
	 *
	 * @param testPlanXlsPath the testPlanXlsPath to set
	 */
	public void setTestPlanXlsPath(String testPlanXlsPath) {
		this.testPlanXlsPath = testPlanXlsPath;
	}

	/**
	 * Gets the data set.
	 *
	 * @return the dataSet
	 */
	public String getDataSet() {
		return dataSet;
	}

	/**
	 * Sets the data set.
	 *
	 * @param dataSet the dataSet to set
	 */
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}

	/**
	 * Gets the repeat no.
	 *
	 * @return the repeatNo
	 */
	public int getRepeatNo() {
		return repeatNo;
	}

	/**
	 * Sets the repeat no.
	 *
	 * @param repeatNo the repeatNo to set
	 */
	public void setRepeatNo(int repeatNo) {
		this.repeatNo = repeatNo;
	}

	/**
	 * Gets the tags to run.
	 *
	 * @return the tagsToRun
	 */
	public String getTagsToRun() {
		return tagsToRun;
	}

	/**
	 * Sets the tags to run.
	 *
	 * @param tagsToRun the tagsToRun to set
	 */
	public void setTagsToRun(String tagsToRun) {
		this.tagsToRun = tagsToRun;
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
	 * Gets the task result.
	 *
	 * @return the taskResult
	 */
	public List<TaskResult> getTaskResult() {
		return taskResult;
	}

	/**
	 * Sets the task result.
	 *
	 * @param taskResult the taskResult to set
	 */
	public void setTaskResult(List<TaskResult> taskResult) {
		this.taskResult = taskResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [TaskID=" + taskID + ", LaneID=" + laneID + ", TaskName=" + taskName + ", TestPlanXlsPath=" + testPlanXlsPath + ", DataSet="
				+ dataSet + ", RepeatNo=" + repeatNo + ", TagsToRun=" + tagsToRun + "]";
	}

}
