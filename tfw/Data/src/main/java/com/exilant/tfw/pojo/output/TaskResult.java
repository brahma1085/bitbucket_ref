package com.exilant.tfw.pojo.output;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.input.TestPlan;

/**
 * The Class TaskResult.
 *
 * @author mohammedfirdos
 */
public class TaskResult implements Serializable {

	/** TaskResult Entity. */
	private static final long serialVersionUID = 1L;

	/** The task result id. */
	private int taskResultID;
	
	/** The task id. */
	private int taskID;
	
	/** The lane id. */
	private int laneID;
	
	/** The test run id. */
	private int testRunID;
	
	/** The run result id. */
	private String runResultID;
	
	/** The report file path. */
	private String reportFilePath;
	
	/** The result. */
	private boolean result;
	
	/** The start date time. */
	private Timestamp startDateTime;
	
	/** The end date time. */
	private Timestamp endDateTime;
	
	/** The percentage pass count. */
	private int percentagePassCount;
	
	/** The percentage fail count. */
	private int percentageFailCount;
	
	/** The end time. */
	private Date endTime;
	
	/** The test plan. */
	private TestPlan testPlan;
	
	/** The agent id. */
	private int agentID;
	
	/** The agent name. */
	private String agentName;
	
	/** The ip. */
	private String ip;
	
	/** The port. */
	private int port;
	
	/** The start time. */
	private Date startTime;
	
	/** The reputation name of task. */
	private String reputationNameOfTask;
	
	/** The agent os. */
	private String agentOs;
	
	/** The lane name. */
	private String laneName;
	
	/** The lane clone id. */
	private int laneCloneId;
	
	/** The task repeat id. */
	private int taskRepeatId;
	
	/** The lane rpt. */
	private int laneRpt;
	
	/** The task name. */
	private String taskName;
	
	/** The test suite file path. */
	private String testSuiteFilePath;
	
	/** The lane number in plan. */
	private int laneNumberInPlan;

	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The test plan results. */
	private List<TestPlanResult> testPlanResults = new ArrayList<TestPlanResult>(0);

	/**
	 * Gets the test plan results.
	 *
	 * @return the test plan results
	 */
	public List<TestPlanResult> getTestPlanResults() {
		return testPlanResults;
	}

	/**
	 * Sets the test plan results.
	 *
	 * @param testPlanResults the new test plan results
	 */
	public void setTestPlanResults(List<TestPlanResult> testPlanResults) {
		this.testPlanResults = testPlanResults;
	}

	/**
	 * Gets the agent os.
	 *
	 * @return the agent os
	 */
	public String getAgentOs() {
		return agentOs;
	}

	/**
	 * Sets the agent os.
	 *
	 * @param agentOs the new agent os
	 */
	public void setAgentOs(String agentOs) {
		this.agentOs = agentOs;
	}

	/**
	 * Gets the reputation name of task.
	 *
	 * @return the reputation name of task
	 */
	public String getReputationNameOfTask() {
		return reputationNameOfTask;
	}

	/**
	 * Sets the reputation name of task.
	 *
	 * @param reputationNameOfTask the new reputation name of task
	 */
	public void setReputationNameOfTask(String reputationNameOfTask) {
		this.reputationNameOfTask = reputationNameOfTask;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the lane rpt.
	 *
	 * @return the lane rpt
	 */
	public int getLaneRpt() {
		return laneRpt;
	}

	/**
	 * Sets the lane rpt.
	 *
	 * @param laneRpt the new lane rpt
	 */
	public void setLaneRpt(int laneRpt) {
		this.laneRpt = laneRpt;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the test plan.
	 *
	 * @return the test plan
	 */
	public TestPlan getTestPlan() {
		return testPlan;
	}

	/**
	 * Sets the test plan.
	 *
	 * @param testPlan the new test plan
	 */
	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	/**
	 * Gets the agent id.
	 *
	 * @return the agent id
	 */
	public int getAgentID() {
		return agentID;
	}

	/**
	 * Sets the agent id.
	 *
	 * @param agentID the new agent id
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * Gets the agent name.
	 *
	 * @return the agent name
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Sets the agent name.
	 *
	 * @param agentName the new agent name
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Sets the lane number in plan.
	 *
	 * @param laneNumberInPlan the new lane number in plan
	 */
	public void setLaneNumberInPlan(int laneNumberInPlan) {
		this.laneNumberInPlan = laneNumberInPlan;
	}

	/**
	 * Gets the task result id.
	 *
	 * @return the taskResultID
	 */
	public int getTaskResultID() {
		return taskResultID;
	}

	/**
	 * Sets the task result id.
	 *
	 * @param taskResultID the taskResultID to set
	 */
	public void setTaskResultID(int taskResultID) {
		this.taskResultID = taskResultID;
	}

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
	 * Gets the run result id.
	 *
	 * @return the runResultID
	 */
	public String getRunResultID() {
		return runResultID;
	}

	/**
	 * Sets the run result id.
	 *
	 * @param runResultID the runResultID to set
	 */
	public void setRunResultID(String runResultID) {
		this.runResultID = runResultID;
	}

	/**
	 * Gets the report file path.
	 *
	 * @return the reportFilePath
	 */
	public String getReportFilePath() {
		return reportFilePath;
	}

	/**
	 * Sets the report file path.
	 *
	 * @param reportFilePath the reportFilePath to set
	 */
	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	/**
	 * Gets the test run id.
	 *
	 * @return the testRunID
	 */
	public int getTestRunID() {
		return testRunID;
	}

	/**
	 * Checks if is result.
	 *
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * Gets the start date time.
	 *
	 * @return the startDateTime
	 */
	public Timestamp getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Sets the start date time.
	 *
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * Gets the end date time.
	 *
	 * @return the endDateTime
	 */
	public Timestamp getEndDateTime() {
		return endDateTime;
	}

	/**
	 * Sets the end date time.
	 *
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * Gets the percentage pass count.
	 *
	 * @return the percentagePassCount
	 */
	public int getPercentagePassCount() {
		return percentagePassCount;
	}

	/**
	 * Sets the percentage pass count.
	 *
	 * @param percentagePassCount the percentagePassCount to set
	 */
	public void setPercentagePassCount(int percentagePassCount) {
		this.percentagePassCount = percentagePassCount;
	}

	/**
	 * Gets the percentage fail count.
	 *
	 * @return the percentageFailCount
	 */
	public int getPercentageFailCount() {
		return percentageFailCount;
	}

	/**
	 * Sets the percentage fail count.
	 *
	 * @param percentageFailCount the percentageFailCount to set
	 */
	public void setPercentageFailCount(int percentageFailCount) {
		this.percentageFailCount = percentageFailCount;
	}

	/**
	 * Sets the test run id.
	 *
	 * @param testRunID the testRunID to set
	 */
	public void setTestRunID(int testRunID) {
		this.testRunID = testRunID;
	}

	/**
	 * Gets the scheduler run details.
	 *
	 * @return the schedulerRunDetails
	 */
	public List<SchedulerRunDetails> getSchedulerRunDetails() {
		return schedulerRunDetails;
	}

	/**
	 * Sets the scheduler run details.
	 *
	 * @param schedulerRunDetails the schedulerRunDetails to set
	 */
	public void setSchedulerRunDetails(List<SchedulerRunDetails> schedulerRunDetails) {
		this.schedulerRunDetails = schedulerRunDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskResult [TaskResultID=" + taskResultID + ", TaskID=" + taskID + ", LaneID=" + laneID + ", TestRunID=" + testRunID + ", RunResultID="
				+ runResultID + ", ReportFilePath=" + reportFilePath + ", Result=" + result + ", StartDateTime=" + startDateTime + ", EndDateTime="
				+ endDateTime + ", PercentagePassCount=" + percentagePassCount + ", PercentageFailCount=" + percentageFailCount + "]";
	}

	/**
	 * Gets the lane name.
	 *
	 * @return the lane name
	 */
	public String getLaneName() {
		return laneName;
	}

	/**
	 * Sets the lane name.
	 *
	 * @param laneName the new lane name
	 */
	public void setLaneName(String laneName) {
		this.laneName = laneName;
	}

	/**
	 * Gets the lane clone id.
	 *
	 * @return the lane clone id
	 */
	public int getLaneCloneId() {
		return laneCloneId;
	}

	/**
	 * Sets the lane clone id.
	 *
	 * @param cloneId the new lane clone id
	 */
	public void setLaneCloneId(int cloneId) {
		laneCloneId = cloneId;
	}

	/**
	 * Gets the lane repeat id.
	 *
	 * @param laneRpt the lane rpt
	 * @return the lane repeat id
	 */
	public int getLaneRepeatId(int laneRpt) {
		return laneRpt;
	}

	/**
	 * Sets the lane repeat id.
	 *
	 * @param laneRpt the new lane repeat id
	 */
	public void setLaneRepeatId(int laneRpt) {
		this.laneRpt = laneRpt;
	}

	/**
	 * Gets the task repeat id.
	 *
	 * @return the task repeat id
	 */
	public int getTaskRepeatId() {
		return taskRepeatId;
	}

	/**
	 * Sets the task repeat id.
	 *
	 * @param taskRepeatId the new task repeat id
	 */
	public void setTaskRepeatId(int taskRepeatId) {
		this.taskRepeatId = taskRepeatId;
	}

	/**
	 * Gets the task name.
	 *
	 * @return the task name
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the task name.
	 *
	 * @param taskName the new task name
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Gets the test suite file path.
	 *
	 * @return the test suite file path
	 */
	public String getTestSuiteFilePath() {
		return testSuiteFilePath;
	}

	/**
	 * Sets the test suite file path.
	 *
	 * @param testPlanXlsPath the new test suite file path
	 */
	public void setTestSuiteFilePath(String testPlanXlsPath) {
		this.testSuiteFilePath = testPlanXlsPath;
	}

	/**
	 * Gets the lane number in plan.
	 *
	 * @return the lane number in plan
	 */
	public int getLaneNumberInPlan() {
		return laneNumberInPlan;
	}

	/**
	 * Sets the lane no.
	 *
	 * @param laneNumberInPlan the new lane no
	 */
	public void setLaneNo(int laneNumberInPlan) {
		this.laneNumberInPlan = laneNumberInPlan;
	}

	/**
	 * Gets the agent os.
	 *
	 * @return the agent os
	 */
	public String getAgentOS() {
		return agentOs;
	}

	/**
	 * Sets the agent os.
	 *
	 * @param agentOs the new agent os
	 */
	public void setAgentOS(String agentOs) {
		this.agentOs = agentOs;
	}

}
